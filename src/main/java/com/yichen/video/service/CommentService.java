package com.yichen.video.service;

import com.github.pagehelper.PageInfo;
import com.yichen.video.dto.CheckVideoDto;
import com.yichen.video.dto.UploadDto;
import com.yichen.video.model.Comment;
import com.yichen.video.vo.Result;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommentService {

    PageInfo<Comment> getComment(int pageNum, int pageSize ,Long videoId);

}
