package com.yichen.video.controller;

import com.github.pagehelper.PageInfo;
import com.yichen.video.model.Comment;
import com.yichen.video.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {


    @Autowired
    CommentService commentService;

    @GetMapping()
    public PageInfo<Comment> getPageById(@RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "10") int pageSize
            , @RequestParam Long videoId) {


        return commentService.getComment(pageNum,pageSize,videoId);
    }
}
