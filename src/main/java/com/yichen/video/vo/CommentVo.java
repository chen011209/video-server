package com.yichen.video.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private Long commentId;

    private Long time;

    private Long videoId;

    private String content;

    private Integer likes;

    private Long userId;

    private String userName;

    private String avatarPath;

    private Boolean isLike;

}
