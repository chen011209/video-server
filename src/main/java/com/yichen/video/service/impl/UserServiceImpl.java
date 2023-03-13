package com.yichen.video.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yichen.video.Redis.RedisCache;
import com.yichen.video.dao.UserMapper;
import com.yichen.video.dto.LoginDto;
import com.yichen.video.dto.RegisterDto;
import com.yichen.video.enums.ErrorEnum;
import com.yichen.video.enums.UserTypeEnum;
import com.yichen.video.model.User;
import com.yichen.video.model.UserExample;
import com.yichen.video.service.UserService;
import com.yichen.video.security.LoginUser;
import com.yichen.video.util.JwtUtil;
import com.yichen.video.vo.Result;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Autowired
    UserMapper userMapper;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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


    class UserInfo{
        String token;
        Integer userType;

        public UserInfo(String token, Integer userType) {
            this.token = token;
            this.userType = userType;
        }
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
        try {
            User user = new User();

            user.setUserName(registerDto.getUserName());
            user.setUserEmail(registerDto.getUserEmail());
            user.setSignature(registerDto.getSignature());
            user.setType(registerDto.getType());
            user.setUserLevel((byte) 0);
            user.setUserLevelPoints(0);
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));


            userMapper.insert(user);
        }catch (Exception e){
            logger.error("注册失败:"+e.getMessage());

            return Result.fail(ErrorEnum.REGISTER_FAILED.getCode(), "注册失败");
        }

        return Result.ok();
    }



    @Override
    public void getUser() {

    }
}
