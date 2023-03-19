package com.yichen.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String userName;

    private Byte userLevel;

    private Integer userLevelPoints;

    private String userEmail;

    private String signature;

    private String password;

    private Byte type;
}
