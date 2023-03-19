package com.yichen.video.dao;

import com.yichen.video.model.CheckVideo;
import com.yichen.video.model.CheckVideoExample;
import java.util.List;

import com.yichen.video.vo.CheckVideoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CheckVideoMapper {
    /*
    myCode
     */
    List<CheckVideoVo> getCheckVideoList(Integer limit);

    long countByExample(CheckVideoExample example);

    int deleteByExample(CheckVideoExample example);

    int deleteByPrimaryKey(Long checkVideoId);

    int insert(CheckVideo row);

    int insertSelective(CheckVideo row);

    List<CheckVideo> selectByExample(CheckVideoExample example);

    CheckVideo selectByPrimaryKey(Long checkVideoId);

    int updateByExampleSelective(@Param("row") CheckVideo row, @Param("example") CheckVideoExample example);

    int updateByExample(@Param("row") CheckVideo row, @Param("example") CheckVideoExample example);

    int updateByPrimaryKeySelective(CheckVideo row);

    int updateByPrimaryKey(CheckVideo row);
}