package com.yichen.video.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yichen.video.dao.CommentMapper;
import com.yichen.video.model.Comment;
import com.yichen.video.model.CommentExample;
import com.yichen.video.service.CommentService;
import com.yichen.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("CommentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;


    @Override
    public PageInfo<Comment> getComment(int pageNum, int pageSize,Long videoId) {


        PageHelper.startPage(pageNum, pageSize);
        // 调用Mapper接口方法


        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause("likes desc");
        commentExample.createCriteria().andVideoIdEqualTo(videoId);
//
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
//         封装分页结果为PageInfo对象并返回
        return new PageInfo<>(commentList);



    }
}
