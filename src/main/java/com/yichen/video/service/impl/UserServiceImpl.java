package com.yichen.video.service.impl;

import com.yichen.video.service.UserService;
import com.yichen.video.service.VideoService;
import com.yichen.video.util.NonStaticResourceHttpRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service("UserService")
public class UserServiceImpl implements UserService {



    @Override
    public void getUser() {

    }
}
