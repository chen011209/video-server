package com.yichen.video.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.yichen.video.service.VideoService;
import com.yichen.video.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/video")
@CrossOrigin
public class VideoController {


    @Autowired
    private VideoService videoService;



    @PostMapping("/upload")
    public void postVideo(HttpServletRequest request, HttpServletResponse response
            ,@RequestParam("video") MultipartFile video,@RequestParam("picture") MultipartFile picture
            ,@RequestParam String title
    ){

        System.out.println(video.getSize());
        System.out.println(picture.getSize());
        System.out.println(title);

    }

    @PostMapping("/uploadfile")
    public Result postFile(HttpServletRequest request, HttpServletResponse response
            , @RequestParam("file") MultipartFile file
    ){


        System.out.println(file.getSize());
        return Result.ok(file.getSize());
//        return videoService.uploadFile(file);

    }





    @GetMapping
    public void getVideo(HttpServletRequest request, HttpServletResponse response,@RequestParam String videoId) throws Exception{
        videoService.getVideo(request,response,videoId);
    }




}
