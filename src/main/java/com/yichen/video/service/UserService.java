package com.yichen.video.service;

import com.github.pagehelper.PageInfo;
import com.yichen.video.dto.AvatarDto;
import com.yichen.video.dto.EditDto;
import com.yichen.video.dto.LoginDto;
import com.yichen.video.dto.RegisterDto;
import com.yichen.video.vo.Result;
import com.yichen.video.vo.UserVo;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    Result loginByPassword(LoginDto loginDto);

    Result logOut();

    Result register(RegisterDto registerDto);

    void getAvatar(HttpServletResponse response,String avatarPath);

    Result editUser(EditDto editDto);


    Result editAvatar(AvatarDto avatarDto);

    Result getUserInfo(Long userId);

    Result sendMail(String emailReceiver);

    Result followUser(Long userId);

    PageInfo<UserVo> followList(int pageNum, int pageSize);


    Result getIndividualInfo(Long videoId);

}
