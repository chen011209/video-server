package com.yichen.video.service;

import com.github.pagehelper.PageInfo;
import com.yichen.video.dto.*;
import com.yichen.video.vo.Result;
import com.yichen.video.vo.UserVo;

import javax.servlet.http.HttpServletResponse;

public interface ScoreService {


    Result scoreVideo(ScoreVideoDto scoreVideoDto);

    Result getScore(Long videoId);

}
