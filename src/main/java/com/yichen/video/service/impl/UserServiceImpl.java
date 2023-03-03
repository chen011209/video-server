package com.yichen.video.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yichen.video.Redis.RedisCache;
import com.yichen.video.dto.LoginDto;
import com.yichen.video.service.UserService;
import com.yichen.video.security.LoginUser;
import com.yichen.video.util.JwtUtil;
import com.yichen.video.vo.Result;
import com.yichen.video.vo.User;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    /**
     * 根据用户名密码进行登录
     * @param loginDto
     * @return
     */
    @Override
    public Result loginByPassword(LoginDto loginDto) {

        // 第一个参数是账号 第二个参数是密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getMail(),loginDto.getPassword());
        Authentication authenticate;
        try{
            authenticate = authenticationManager.authenticate(authenticationToken);
            // 如果认证没通过 则给出相应提示
//            if (ObjectUtil.isEmpty(authenticate)) {
//                logger.error("用户名或密码错误！");
//                throw new RuntimeException("用户名或密码错误！");
//            }
        }catch (Exception e){
            logger.error("用户名或密码错误！");
            return Result.fail("用户名或密码错误");

        }


        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);


        logger.info("用户成功登录,id为:{}",loginUser.getUser().getId());

        return Result.ok(map);
    }


    @Override
    public void getUser() {

    }
}
