package com.yichen.video.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.yichen.video.dto.UploadDto;
import com.yichen.video.service.VideoService;
import com.yichen.video.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;


@RestController
@RequestMapping("/video")
@CrossOrigin
public class VideoController {


    @Autowired
    private VideoService videoService;



    @PostMapping("/upload")
    public Result postVideo(HttpServletRequest request, HttpServletResponse response
            , @RequestBody UploadDto uploadDto){


        return videoService.uploadVideo(uploadDto);
    }

    @PostMapping("/uploadfile")
    public Result uploadFile(HttpServletRequest request, HttpServletResponse response
            , @RequestParam("file") MultipartFile file
    ){
//        return Result.ok(file.getSize());
        return videoService.uploadFile(file);

    }


//    @GetMapping("/videoList")
//    public Result getVideoList(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        return videoService.getCheckVideoList();
//    }


    @GetMapping("/checkVideoList")
    public Result getCheckVideoList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        return videoService.getCheckVideoList();
    }





    @GetMapping("/video")
    public void getVideo(HttpServletRequest request, HttpServletResponse response,@RequestParam String videoPath) throws Exception{
        videoService.getVideo(request,response,videoPath);
    }


    @GetMapping("/picture")
    public void getPicture(HttpServletRequest request, HttpServletResponse response,@RequestParam String picturePath) throws Exception{

//        http://localhost:8080/video/picture?picturePath=D:/bishe//video-repository/picture/39/1678626562935.jpg

        videoService.getPicture(response,picturePath);

    }

}
