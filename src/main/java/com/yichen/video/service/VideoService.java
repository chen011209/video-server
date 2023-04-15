package com.yichen.video.service;

import com.github.pagehelper.PageInfo;
import com.yichen.video.dto.CheckVideoDto;
import com.yichen.video.dto.CollectDto;
import com.yichen.video.dto.UploadDto;
import com.yichen.video.model.Comment;
import com.yichen.video.vo.Result;
import com.yichen.video.vo.VideoVo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VideoService {
    void getVideo(HttpServletRequest request, HttpServletResponse response,String videoPath) throws Exception;



    Result uploadFile(MultipartFile file);


    Result uploadVideo(UploadDto uploadDto);


    Result getCheckVideoList(Integer len);

    void getPicture(HttpServletResponse response,String picturePath);

    Result checkVideo(CheckVideoDto checkVideoDto);


    Result getCheckVideoInfo(Long checkVideoId);

    Result getVideoInfo(Long videoId);




    PageInfo<VideoVo> getPopularVideoList(int pageNum, int pageSize);

    Result collectVideo(CollectDto collectDto);

    PageInfo<VideoVo> getCollectList(int pageNum, int pageSize);

    PageInfo<VideoVo> getHistoryList(int pageNum, int pageSize);

    PageInfo<VideoVo> getSearchList(int pageNum, int pageSize,String content);


    PageInfo<VideoVo> getVideoList(int pageNum, int pageSize,Long userId);

    PageInfo<VideoVo> getUserVideoList(int pageNum, int pageSize);


}
