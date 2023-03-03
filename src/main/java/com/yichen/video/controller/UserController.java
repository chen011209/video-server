package com.yichen.video.controller;


import com.yichen.video.dao.UserMapper;
import com.yichen.video.dto.LoginDto;
import com.yichen.video.service.UserService;
import com.yichen.video.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping()
public class UserController {


    @Autowired
    UserService userService;


    @PostMapping("/user/login")
    public Result login(HttpServletRequest request, HttpServletResponse response,@RequestBody LoginDto loginDto) {
        return userService.loginByPassword(loginDto);
    }







//    @Autowired
//    UserMapper userMapper;

    @GetMapping
    public void getVideo(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        videoService.getVideo(request,response,videoId);

//        System.out.println(userDao.getUser());
//        System.out.println(userDao.getUser());


//        System.out.println(userMapper.selectByPrimaryKey(1L).getUserId());
    }



}
