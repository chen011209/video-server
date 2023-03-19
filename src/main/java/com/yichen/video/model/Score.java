package com.yichen.video.model;

public class Score {
    private Long scoreId;

    private Long userId;

    private Long videoId;

    private Byte score;

    private Long scoreTime;

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Byte getScore() {
        return score;
    }

    public void setScore(Byte score) {
        this.score = score;
    }

    public Long getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(Long scoreTime) {
        this.scoreTime = scoreTime;
    }
}