package com.yichen.video.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Long id;
    private LocalDateTime birthday;
    private String gender;
    private String username;
    private String password;
    private String telephone;
    private String station;
    private String remark;
    private String mail;
}
