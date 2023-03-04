package com.yichen.video.security;


import com.yichen.video.dao.UserMapper;


import com.yichen.video.model.User;
import com.yichen.video.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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


        UserExample userExample = new UserExample();
        userExample.setDistinct(true);
        UserExample.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andUserEmailEqualTo(mail);


        //只要按照mail将数据库中的user取出(主要是密码) 后续security会在authenticate方法中比对密码
        User user = userMapper.selectByExample(userExample).get(0);


//        if (userList.size()==0) {
//            throw new RuntimeException("用户账号或密码错误");
//        }

        // 因为返回值是UserDetails，所有需要定义一个类，实现UserDetails，把用户信息封装在其中
        //根据用户查询权限信息 添加到LoginUser中
        List<String> list;
        if(user.getType()==1){
            list = new ArrayList<>(Arrays.asList("USER"));
        }else {
            list = new ArrayList<>(Arrays.asList("ADMIN"));

        }
        return new LoginUser(user,list);

    }
}

