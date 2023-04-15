package com.yichen.video.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private Long userId;

    private String userName;

    private Byte userLevel;

    private Integer userLevelPoints;

    private Integer maxLevelPoints;

    private String userEmail;

    private String signature;


    private Byte type;

    private Long creTime;

    private String avatarPath;


    //当前登录用户是否关注该用户
    private Boolean isFollow;
}
