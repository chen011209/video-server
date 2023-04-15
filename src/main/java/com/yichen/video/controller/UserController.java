package com.yichen.video.controller;


import com.github.pagehelper.PageInfo;
import com.yichen.video.Redis.RedisCache;
import com.yichen.video.dao.UserMapper;
import com.yichen.video.dto.AvatarDto;
import com.yichen.video.dto.EditDto;
import com.yichen.video.dto.LoginDto;
import com.yichen.video.dto.RegisterDto;
import com.yichen.video.enums.ErrorEnum;
import com.yichen.video.enums.UserTypeEnum;
import com.yichen.video.service.UserService;
import com.yichen.video.util.UserUtil;
import com.yichen.video.vo.Result;
import com.yichen.video.vo.UserVo;
import com.yichen.video.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(HttpServletRequest request, HttpServletResponse response,@RequestBody LoginDto loginDto) {
        return userService.loginByPassword(loginDto);
    }

    @PostMapping("/logout")
    public Result logOut(HttpServletRequest request, HttpServletResponse response) {
        return userService.logOut();
    }


    @PostMapping("/register")
    public Result registerUser(HttpServletRequest request, HttpServletResponse response, @RequestBody RegisterDto registerDto) {


        registerDto.setType(UserTypeEnum.USER.getCode().byteValue());
        return userService.register(registerDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/register")
    public Result registerAdmin(HttpServletRequest request, HttpServletResponse response, @RequestBody RegisterDto registerDto) {
        registerDto.setType(UserTypeEnum.ADMIN.getCode().byteValue());
        return userService.register(registerDto);
    }




    @GetMapping("/info")
//    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Result getUserInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam Long userId) {
//        System.out.println("user");

        return userService.getUserInfo(userId);
    }

    @GetMapping("/info/my")
//    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Result getMyInfo(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("user");

        return userService.getUserInfo(UserUtil.getUserId());
    }

    @PostMapping("/avatar")
//    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Result editAvatar(HttpServletRequest request, HttpServletResponse response,@RequestBody AvatarDto avatarDto) {
//        System.out.println("user");

        return userService.editAvatar(avatarDto);
    }


    @PostMapping("/edit")
    public Result editUser(HttpServletRequest request, HttpServletResponse response, @RequestBody EditDto editDto) {

        return userService.editUser(editDto);
    }



    @PostMapping("/sendEmail")
    public Result sendMail(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "emailReceiver") String emailReceiver) {
        return  userService.sendMail(emailReceiver);
    }









//    @PostMapping
//    public Result getVer

//    @Autowired
//    UserMapper userMapper;

//    @GetMapping
//    public void getVideo(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        System.out.println("good!");
//        videoService.getVideo(request,response,videoId);

//        System.out.println(userDao.getUser());
//        System.out.println(userDao.getUser());


//        System.out.println(userMapper.selectByPrimaryKey(1L).getUserId());
//    }


    @GetMapping("/avatar")
    public void getAvatar(HttpServletRequest request, HttpServletResponse response,@RequestParam String avatarPath){
        userService.getAvatar(response,avatarPath);



    }





    @PostMapping("/follow/{userId}")
    public Result followUser(HttpServletRequest request, HttpServletResponse response, @PathVariable Long userId) {
        return  userService.followUser(userId);
    }



    @GetMapping("/follow")
    public PageInfo<UserVo> followList(HttpServletRequest request, HttpServletResponse response , @RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "10") int pageSize) {
        return  userService.followList(pageNum,pageSize);
    }



}
