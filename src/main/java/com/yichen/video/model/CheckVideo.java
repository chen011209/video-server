package com.yichen.video.model;

public class CheckVideo {
    private Long checkVideoId;

    private String checkVideoTitle;

    private String checkVideoIntroduction;

    private Long userId;

    private Byte status;

    private Long uploadTime;

    private String videoPath;

    private String picturePath;

    private String remark;

    private Long checkTime;

    private Long checkAdminId;

    public Long getCheckVideoId() {
        return checkVideoId;
    }

    public void setCheckVideoId(Long checkVideoId) {
        this.checkVideoId = checkVideoId;
    }

    public String getCheckVideoTitle() {
        return checkVideoTitle;
    }

    public void setCheckVideoTitle(String checkVideoTitle) {
        this.checkVideoTitle = checkVideoTitle == null ? null : checkVideoTitle.trim();
    }

    public String getCheckVideoIntroduction() {
        return checkVideoIntroduction;
    }

    public void setCheckVideoIntroduction(String checkVideoIntroduction) {
        this.checkVideoIntroduction = checkVideoIntroduction == null ? null : checkVideoIntroduction.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath == null ? null : videoPath.trim();
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath == null ? null : picturePath.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Long checkTime) {
        this.checkTime = checkTime;
    }

    public Long getCheckAdminId() {
        return checkAdminId;
    }

    public void setCheckAdminId(Long checkAdminId) {
        this.checkAdminId = checkAdminId;
    }
}