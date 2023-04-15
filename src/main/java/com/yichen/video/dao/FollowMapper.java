package com.yichen.video.dao;

import com.yichen.video.model.Follow;
import com.yichen.video.model.FollowExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowMapper {
    long countByExample(FollowExample example);

    int deleteByExample(FollowExample example);

    int deleteByPrimaryKey(Long followId);

    int insert(Follow row);

    int insertSelective(Follow row);

    List<Follow> selectByExample(FollowExample example);

    Follow selectByPrimaryKey(Long followId);

    int updateByExampleSelective(@Param("row") Follow row, @Param("example") FollowExample example);

    int updateByExample(@Param("row") Follow row, @Param("example") FollowExample example);

    int updateByPrimaryKeySelective(Follow row);

    int updateByPrimaryKey(Follow row);
}