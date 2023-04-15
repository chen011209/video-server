package com.yichen.video.model;

public class Follow {
    private Long followId;

    private Long userId;

    private Long followUserId;

    private Long followTime;

    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public Long getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Long followTime) {
        this.followTime = followTime;
    }
}