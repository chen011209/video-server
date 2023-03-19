package com.yichen.video.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckVideoVo {
    private Long checkVideoId;

    private String checkVideoTitle;

//    private String checkVideoIntroduction;

    private Long userId;

    private String userName;

//    private Long uploadTime;

//    private String videoPath;

    private String picturePath;

}
