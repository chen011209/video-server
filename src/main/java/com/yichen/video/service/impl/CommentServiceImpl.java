package com.yichen.video.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yichen.video.dao.CommentLikeMapper;
import com.yichen.video.dao.CommentMapper;
import com.yichen.video.dao.UserMapper;
import com.yichen.video.dto.CommentDto;
import com.yichen.video.enums.ErrorEnum;
import com.yichen.video.model.*;
import com.yichen.video.service.CommentService;
import com.yichen.video.service.UserService;
import com.yichen.video.util.UserUtil;
import com.yichen.video.util.sensitiveWord.SensitivewordFilter;
import com.yichen.video.vo.CommentVo;
import com.yichen.video.vo.Result;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


@Service("CommentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;


    @Autowired
    CommentLikeMapper commentLikeMapper;

    @Autowired
    SensitivewordFilter sensitivewordFilter;

    @Override
    public PageInfo<CommentVo> getComment(int pageNum, int pageSize,Long videoId) {

        Long userId = UserUtil.getUserId();

        PageHelper.startPage(pageNum, pageSize);
        // 调用Mapper接口方法


        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause("likes desc");
        commentExample.createCriteria().andVideoIdEqualTo(videoId);
//
        List<Comment> commentList = commentMapper.selectByExample(commentExample);


        PageInfo  result = new PageInfo<>(commentList);

        List<CommentVo> commentVoList = new ArrayList<>(commentList.size());



        for (int i = 0; i < commentList.size(); i++) {

            commentVoList.add(new CommentVo());

            try {
                BeanUtils.copyProperties(commentVoList.get(i), commentList.get(i));


                User user = userMapper.selectByPrimaryKey(commentVoList.get(i).getUserId());

                commentVoList.get(i).setUserName(user.getUserName());

                if(user.getAvatarPath()!=null){
                    commentVoList.get(i).setAvatarPath("http://127.0.0.1:8080/video/picture?picturePath="+user.getAvatarPath().replace('\\','/'));

                }

                CommentLikeExample commentLikeExample = new CommentLikeExample();
                commentLikeExample.createCriteria()
                        .andCommentIdEqualTo(commentVoList.get(i).getCommentId())
                        .andUserIdEqualTo(userId);
                List<CommentLike> commentLikeList = commentLikeMapper.selectByExample(commentLikeExample);

                if(commentLikeList.size()!=0){
                    commentVoList.get(i).setIsLike(true);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        result.setList(commentVoList);





//         封装分页结果为PageInfo对象并返回
        return result;



    }

    @Override
    public Result addComment(CommentDto commentDto) {



        //进行敏感词的校验处理
        String content=sensitivewordFilter.replaceSensitiveWord(commentDto.getContent(),1,"*");


        Comment comment = new Comment();
        comment.setContent(content);
        comment.setLikes(0);
        comment.setTime(System.currentTimeMillis());
        comment.setVideoId(commentDto.getVideoId());
        comment.setUserId(UserUtil.getUserId());
        commentMapper.insert(comment);


        return Result.ok();
    }

    @Override
    public Result likeComment(Long commentId) {



        CommentLikeExample commentLikeExample = new CommentLikeExample();
        commentLikeExample.createCriteria()
                .andCommentIdEqualTo(commentId)
                .andUserIdEqualTo(UserUtil.getUserId());

        List<CommentLike> commentLikeList =  commentLikeMapper.selectByExample(commentLikeExample);

        if(commentLikeList.size()!=0){
            return Result.fail(ErrorEnum.LIKE_REPEAT.getCode(),"已经点赞过该评论");
        }


        commentMapper.likeComment(commentId);

        CommentLike commentLike = new CommentLike();
        commentLike.setUserId(UserUtil.getUserId());
        commentLike.setCommentId(commentId);
        commentLikeMapper.insert(commentLike);

        return Result.ok();
    }
}
