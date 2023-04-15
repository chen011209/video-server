package com.yichen.video.dao;

import com.yichen.video.model.Collect;
import com.yichen.video.model.CollectExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectMapper {
    long countByExample(CollectExample example);

    int deleteByExample(CollectExample example);

    int deleteByPrimaryKey(Long collectId);

    int insert(Collect row);

    int insertSelective(Collect row);

    List<Collect> selectByExample(CollectExample example);

    Collect selectByPrimaryKey(Long collectId);

    int updateByExampleSelective(@Param("row") Collect row, @Param("example") CollectExample example);

    int updateByExample(@Param("row") Collect row, @Param("example") CollectExample example);

    int updateByPrimaryKeySelective(Collect row);

    int updateByPrimaryKey(Collect row);
}