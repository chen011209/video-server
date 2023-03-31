package com.yichen.video.controller;

import com.github.pagehelper.PageInfo;
import com.yichen.video.dto.CommentDto;
import com.yichen.video.model.Comment;
import com.yichen.video.service.CommentService;
import com.yichen.video.vo.CommentVo;
import com.yichen.video.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;


@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {


    @Autowired
    CommentService commentService;

    @GetMapping("/popular")
    public PageInfo<CommentVo> getComment(@RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "10") int pageSize
            , @RequestParam Long videoId) {


        return commentService.getComment(pageNum,pageSize,videoId);
    }


    @PostMapping()
    public Result addComment(@RequestBody CommentDto commentDto) {

        return commentService.addComment(commentDto);
    }

    @PostMapping("/like/{commentId}")
    public Result likeComment(@PathVariable Long commentId) {

        return commentService.likeComment(commentId);
    }
}
