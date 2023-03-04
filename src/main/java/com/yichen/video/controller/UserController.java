package com.yichen.video.controller;


import com.yichen.video.dao.UserMapper;
import com.yichen.video.dto.LoginDto;
import com.yichen.video.dto.RegisterDto;
import com.yichen.video.enums.UserTypeEnum;
import com.yichen.video.service.UserService;
import com.yichen.video.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping()
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user/login")
    public Result login(HttpServletRequest request, HttpServletResponse response,@RequestBody LoginDto loginDto) {
        return userService.loginByPassword(loginDto);
    }

    @GetMapping("/user/logout")
    public Result logOut(HttpServletRequest request, HttpServletResponse response) {
        return userService.logOut();
    }


    @PostMapping("/user/register")
    public Result registerUser(HttpServletRequest request, HttpServletResponse response, @RequestBody RegisterDto registerDto) {

        registerDto.setType(UserTypeEnum.USER.getCode().byteValue());
        return userService.register(registerDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user/admin/register")
    public Result registerAdmin(HttpServletRequest request, HttpServletResponse response, @RequestBody RegisterDto registerDto) {
        registerDto.setType(UserTypeEnum.ADMIN.getCode().byteValue());
        return userService.register(registerDto);
    }




    @GetMapping("/user")
//    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("user");
    }






//    @Autowired
//    UserMapper userMapper;

    @GetMapping
    public void getVideo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("good!");
//        videoService.getVideo(request,response,videoId);

//        System.out.println(userDao.getUser());
//        System.out.println(userDao.getUser());


//        System.out.println(userMapper.selectByPrimaryKey(1L).getUserId());
    }



}
