package com.yichen.video.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.yichen.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/video")
public class VideoController {


    @Autowired
    private VideoService videoService;


    @GetMapping
    public void getVideo(HttpServletRequest request, HttpServletResponse response,@RequestParam String videoId) throws Exception{
        videoService.getVideo(request,response,videoId);
    }




}
