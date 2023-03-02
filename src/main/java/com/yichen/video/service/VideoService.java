package com.yichen.video.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VideoService {
    void getVideo(HttpServletRequest request, HttpServletResponse response,String key) throws Exception;
}
