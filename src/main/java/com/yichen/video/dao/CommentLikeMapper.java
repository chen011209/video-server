package com.yichen.video.dao;

import com.yichen.video.model.CommentLike;
import com.yichen.video.model.CommentLikeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentLikeMapper {
    long countByExample(CommentLikeExample example);

    int deleteByExample(CommentLikeExample example);

    int deleteByPrimaryKey(Long likeId);

    int insert(CommentLike row);

    int insertSelective(CommentLike row);

    List<CommentLike> selectByExample(CommentLikeExample example);

    CommentLike selectByPrimaryKey(Long likeId);

    int updateByExampleSelective(@Param("row") CommentLike row, @Param("example") CommentLikeExample example);

    int updateByExample(@Param("row") CommentLike row, @Param("example") CommentLikeExample example);

    int updateByPrimaryKeySelective(CommentLike row);

    int updateByPrimaryKey(CommentLike row);
}