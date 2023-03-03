package com.yichen.video.security;

import com.yichen.video.dao.UserMapper;
import com.yichen.video.vo.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;



    //UsernamePasswordAuthenticationToken authenticationToken
    //= new UsernamePasswordAuthenticationToken(loginDto.getMail(),loginDto.getPassword());
    //username作为调用的第一个参数 用于从数据库中查找
    //password作为第二个参数用于和数据库中查找出的password对比
    //
    //数据库中的password为加密之后的
    //前端传入的password为明文 security会自动调用算法加密 之后再进行比对
    //加密的bean在config中
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        // 根据用户名查询用户信息
//        String name = userMapper.selectByPrimaryKey(1L).getUserName();



        User user = new User();
        user.setId(11L);
//        user.setBirthday(LocalDateTime.now());
//        user.setGender("1234");
//        user.setUsername("1234");
//        user.setMail("1234");

        if(mail.equals("1234")){
            user.setPassword("$2a$10$xcKRWSAEYH6xRnJkofPAtu.qIERpgZMK5U7wSPbkTxGxzIKTRoFbO");
        }
        else
            user.setPassword("$2a$10$xcKRWSAEYH6K5U7wSPbkTxGxzIKTRoFbO");

//        user.setTelephone("1234");
//        user.setStation("1234");
//        user.setRemark("1234");



        /**
         * 若查询不到用户信息，则抛异常
         * SpringSecurity可以处理我们在查询中遇到的异常
         */
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户账号或密码错误");
        }

        // TODO 根据用户查询权限信息，添加到LoginUser中


        // 因为返回值是UserDetails，所有需要定义一个类，实现UserDetails，把用户信息封装在其中
        return new LoginUser(user);
    }
}

