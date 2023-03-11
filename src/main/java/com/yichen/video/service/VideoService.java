package com.yichen.video.service;

import com.yichen.video.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VideoService {
    void getVideo(HttpServletRequest request, HttpServletResponse response,String key) throws Exception;



    Result uploadFile(MultipartFile file);
}
