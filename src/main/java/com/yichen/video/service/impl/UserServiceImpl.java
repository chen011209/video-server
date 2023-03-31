package com.yichen.video.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yichen.video.Redis.RedisCache;
import com.yichen.video.dao.UserMapper;
import com.yichen.video.dto.AvatarDto;
import com.yichen.video.dto.EditDto;
import com.yichen.video.dto.LoginDto;
import com.yichen.video.dto.RegisterDto;
import com.yichen.video.enums.ErrorEnum;
import com.yichen.video.enums.UserTypeEnum;
import com.yichen.video.model.User;
import com.yichen.video.model.UserExample;
import com.yichen.video.service.UserService;
import com.yichen.video.security.LoginUser;
import com.yichen.video.util.JwtUtil;
import com.yichen.video.util.UserUtil;
import com.yichen.video.vo.Result;
import com.yichen.video.vo.UserVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;


@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Autowired
    UserMapper userMapper;

    @Resource
    private JavaMailSender javaMailSender;

    //读取yml文件中username的值并赋值给form
    @Value("${spring.mail.username}")
    private String from;


    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);



    @Value("${resource.picture-path}")
    private String picturePath;

    @Value("${resource.upload-path}")
    private String uploadPath;


    /**
     * 根据用户名密码进行登录
     * @param loginDto
     * @return
     */
    @Override
    public Result loginByPassword(LoginDto loginDto) {

        // 第一个参数是账号 第二个参数是密码 数据在UserDetailsServiceImpl中取
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getMail(),loginDto.getPassword());
        Authentication authenticate;
        try{
            authenticate = authenticationManager.authenticate(authenticationToken);
            // 如果认证没通过 则给出相应提示
//            if (ObjectUtil.isEmpty(authenticate)) {
//                logger.error("用户名或密码错误！");
//                throw new RuntimeException("用户名或密码错误！");
//            }
        }catch (Exception e){
            logger.error("用户名或密码错误！");
            return Result.fail(ErrorEnum.LOGIN_FAILED.getCode(),"用户名或密码错误");

        }


        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);


        String preFix = null;
        //authenticate存入redis   3天后后用户到期
        if(loginUser.getUser().getType()== UserTypeEnum.USER.getCode().byteValue()){
            preFix = "user:";
        }else if(loginUser.getUser().getType()== UserTypeEnum.ADMIN.getCode().byteValue()){
            preFix = "admin:";
        }
//        redisCache.setCacheObject(preFix+userId,loginUser,3, TimeUnit.DAYS);


        //设置用户不到期 除非用户退出登录
        redisCache.setCacheObject(preFix+userId,loginUser);

        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        map.put("userType",loginUser.getUser().getType().toString());


        logger.info("用户成功登录,id为:{}",loginUser.getUser().getUserId());



//        UserInfo userInfo = new UserInfo();


        return Result.ok(map,loginUser.getUser().getType().toString());
    }



    @Override
    public Result logOut() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        User user = loginUser.getUser();

        Long userid = user.getUserId();

        if(user.getType()==UserTypeEnum.USER.getCode().byteValue()){
            redisCache.deleteObject("user:"+userid);
        }else {
            redisCache.deleteObject("admin:"+userid);

        }
        return Result.ok();
    }

    @Override
    public Result register(RegisterDto registerDto) {


        if(registerDto.getVerificationCode().equals(redisCache.getCacheObject("code:"+registerDto.getUserEmail()))==false){
            return Result.fail(ErrorEnum.VERIFICATION_CODE_ERROR.getCode(),"验证码错误");
        }


        try {
            User user = new User();

            user.setUserName(registerDto.getUserName());
            user.setUserEmail(registerDto.getUserEmail());
            user.setSignature(registerDto.getSignature());
            user.setType(registerDto.getType());
            user.setUserLevel((byte) 0);
            user.setUserLevelPoints(0);
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setCreTime(System.currentTimeMillis());


            userMapper.insert(user);
        }catch (Exception e){
            logger.error("注册失败:"+e.getMessage());

            return Result.fail(ErrorEnum.REGISTER_FAILED.getCode(), "注册失败");
        }

        return Result.ok();
    }

    @Override
    public void getAvatar(HttpServletResponse response, String avatarPath) {
        try{
            //        File file = new File("D:\\bishe\\video-repository\\picture\\39\\1678626562935.jpg");
            File file = new File(avatarPath);
            BufferedImage image = ImageIO.read(file);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] imageData = baos.toByteArray();

            response.setContentType("image/jpeg");
            response.setContentLength(imageData.length);
            response.getOutputStream().write(imageData);
            response.getOutputStream().flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public Result editUser(EditDto editDto) {
        User user = new User();
        user.setUserId(UserUtil.getUserId());
        user.setUserName(editDto.getUserName());
        user.setSignature(editDto.getSignature());
        userMapper.updateByPrimaryKeySelective(user);


        return Result.ok(user);
    }

    @Override
    public Result editAvatar(AvatarDto avatarDto) {
        Long userid = UserUtil.getUserId();


        File pictureFolder = new File(picturePath+'\\'+userid.toString());
        if (!pictureFolder.isDirectory()) {
            pictureFolder.mkdirs();
        }

        String pictureSavePth;
        try {
            pictureSavePth = picturePath +'\\'+userid.toString() + '\\' + System.currentTimeMillis() + avatarDto.getAvatarPath().substring(avatarDto.getAvatarPath().lastIndexOf("."));

        }catch (Exception e){
            return Result.fail(ErrorEnum.FILE_ERROR.getCode(), "文件错误，无法找到指定文件");
        }


        try(InputStream pictureInputStream = new FileInputStream(avatarDto.getAvatarPath());
            OutputStream pictureOutputStream = new FileOutputStream(pictureSavePth);){


            byte[] buffer = new byte[1024];
            int length;

            while ((length = pictureInputStream.read(buffer)) > 0) {
                pictureOutputStream.write(buffer, 0, length);
            }

        }catch (Exception e){
            return Result.fail(ErrorEnum.FILE_ERROR.getCode(), "文件错误，无法找到指定文件");
        }


        User user = new User();
        user.setAvatarPath(pictureSavePth);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andUserIdEqualTo(UserUtil.getUserId());
        userMapper.updateByExampleSelective(user,userExample);





        return Result.ok("http://127.0.0.1:8080/video/picture?picturePath="+pictureSavePth.replace('\\','/'));
    }

    @Override
    public Result getUserInfo(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);

        //将实体类转为VO
        UserVo userVo = new UserVo();
        try {
            BeanUtils.copyProperties(userVo, user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if(userVo.getAvatarPath()!=null){
            userVo.setAvatarPath("http://127.0.0.1:8080/video/picture?picturePath="+userVo.getAvatarPath().replace('\\','/'));

        }

        if(userVo.getSignature() == null){
            userVo.setSignature("这个用户很懒，还没有签名。");
        }

        if(userVo.getUserLevel()==7){
            userVo.setMaxLevelPoints(1);
            userVo.setUserLevelPoints(1);
        }else {
            userVo.setMaxLevelPoints(UserUtil.getMaxLevelPoints(Integer.valueOf(userVo.getUserLevel())));
        }

        return Result.ok(userVo);
    }

    @Override
    public Result sendMail(String emailReceiver) {
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件发送者
        message.setFrom(from);
        // 设置邮件接收者
        message.setTo(emailReceiver);
        // 设置邮件的主题
        message.setSubject("注册验证码");
        // 设置邮件的正文
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int r = random.nextInt(10);
            code.append(r);
        }

        redisCache.setCacheObject("code:"+emailReceiver,code,2,TimeUnit.MINUTES);

        String text = "您的验证码为：" + code + ",有效期为两分钟,请勿泄露给他人。";
        message.setText(text);
        // 发送邮件
        try {
            javaMailSender.send(message);
            return Result.ok("发送成功");
        } catch (MailException e) {
            e.printStackTrace();
        }
        return Result.fail(ErrorEnum.MAIL_ERROR.getCode(),"发送失败");

    }


}
