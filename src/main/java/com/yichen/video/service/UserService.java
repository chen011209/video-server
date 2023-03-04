package com.yichen.video.service;

import com.yichen.video.dto.LoginDto;
import com.yichen.video.dto.RegisterDto;
import com.yichen.video.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    Result loginByPassword(LoginDto loginDto);

    Result logOut();

    Result register(RegisterDto registerDto);




    void getUser();
}
