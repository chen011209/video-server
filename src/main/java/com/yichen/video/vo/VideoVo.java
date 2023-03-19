package com.yichen.video.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoVo {
    private Long videoId;

    private String videoTitle;

//    private String checkVideoIntroduction;

    private Long userId;

    private String userName;

//    private Long uploadTime;

//    private String videoPath;

    private String picturePath;

    private Integer viewNum;

}
