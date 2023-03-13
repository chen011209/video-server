package com.yichen.video.service;

import com.yichen.video.dto.UploadDto;
import com.yichen.video.vo.Result;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VideoService {
    void getVideo(HttpServletRequest request, HttpServletResponse response,String videoPath) throws Exception;



    Result uploadFile(MultipartFile file);


    Result uploadVideo(UploadDto uploadDto);


    Result getCheckVideoList();

    void getPicture(HttpServletResponse response,String picturePath);

}
