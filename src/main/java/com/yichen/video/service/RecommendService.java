package com.yichen.video.service;


import com.yichen.video.dto.CollectDto;
import com.yichen.video.dto.ScoreVideoDto;
import com.yichen.video.vo.Result;

public interface RecommendService {
    Result getRecommend();

    Result scoreVideo(ScoreVideoDto scoreVideoDto);

    Result getScore(Long videoId);


    Result getIndividualInfo(Long videoId);

}
