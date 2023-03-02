package com.yichen.video.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    UserDao userDao;

    @GetMapping
    public void getVideo(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        videoService.getVideo(request,response,videoId);

//        System.out.println(userDao.getUser());
//        System.out.println(userDao.getUser());
    }



}
