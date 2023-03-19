package com.yichen.video.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadDto {

    private String videoPath;

    private String picturePath;

    private String introduction;

    private String title;

}
