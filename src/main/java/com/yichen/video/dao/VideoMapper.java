package com.yichen.video.dao;

import com.yichen.video.model.Video;
import com.yichen.video.model.VideoExample;
import java.util.List;

import com.yichen.video.vo.VideoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VideoMapper {
    /*
myCode
*/
    List<VideoVo> getVideoList();

    List<VideoVo> getRandomVideoList(Integer start,Integer end);

    VideoVo getVideoVo(Long videoId);

    long countByExample(VideoExample example);

    int deleteByExample(VideoExample example);

    int deleteByPrimaryKey(Long videoId);

    int insert(Video row);

    int insertSelective(Video row);

    List<Video> selectByExample(VideoExample example);

    Video selectByPrimaryKey(Long videoId);

    int updateByExampleSelective(@Param("row") Video row, @Param("example") VideoExample example);

    int updateByExample(@Param("row") Video row, @Param("example") VideoExample example);

    int updateByPrimaryKeySelective(Video row);

    int updateByPrimaryKey(Video row);
}