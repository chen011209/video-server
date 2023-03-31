package com.yichen.video.model;

public class User {
    private Long userId;

    private String userName;

    private Byte userLevel;

    private Integer userLevelPoints;

    private String userEmail;

    private String signature;

    private String password;

    private Byte type;

    private Long creTime;

    private String avatarPath;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Byte getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Byte userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getUserLevelPoints() {
        return userLevelPoints;
    }

    public void setUserLevelPoints(Integer userLevelPoints) {
        this.userLevelPoints = userLevelPoints;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getCreTime() {
        return creTime;
    }

    public void setCreTime(Long creTime) {
        this.creTime = creTime;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath == null ? null : avatarPath.trim();
    }
}