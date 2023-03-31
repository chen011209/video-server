package com.yichen.video.dao;

import com.yichen.video.model.User;
import com.yichen.video.model.UserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(User row);

    int insertSelective(User row);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("row") User row, @Param("example") UserExample example);

    int updateByExample(@Param("row") User row, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);
}