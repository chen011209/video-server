package com.yichen.video.dao;

import com.yichen.video.model.History;
import com.yichen.video.model.HistoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HistoryMapper {
    long countByExample(HistoryExample example);

    int deleteByExample(HistoryExample example);

    int deleteByPrimaryKey(Long historyId);

    int insert(History row);

    int insertSelective(History row);

    List<History> selectByExample(HistoryExample example);

    History selectByPrimaryKey(Long historyId);

    int updateByExampleSelective(@Param("row") History row, @Param("example") HistoryExample example);

    int updateByExample(@Param("row") History row, @Param("example") HistoryExample example);

    int updateByPrimaryKeySelective(History row);

    int updateByPrimaryKey(History row);
}