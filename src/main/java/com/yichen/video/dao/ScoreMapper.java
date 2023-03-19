package com.yichen.video.dao;

import com.yichen.video.model.Score;
import com.yichen.video.model.ScoreExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScoreMapper {
    long countByExample(ScoreExample example);

    int deleteByExample(ScoreExample example);

    int deleteByPrimaryKey(Long scoreId);

    int insert(Score row);

    int insertSelective(Score row);

    List<Score> selectByExample(ScoreExample example);

    Score selectByPrimaryKey(Long scoreId);

    int updateByExampleSelective(@Param("row") Score row, @Param("example") ScoreExample example);

    int updateByExample(@Param("row") Score row, @Param("example") ScoreExample example);

    int updateByPrimaryKeySelective(Score row);

    int updateByPrimaryKey(Score row);
}