package com.yichen.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto implements Serializable {
//    private String username;
    private String mail;
    private String password;
//    private String telephone;
}
