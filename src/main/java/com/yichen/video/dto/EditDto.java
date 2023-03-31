package com.yichen.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditDto implements Serializable {
    private String userName;

    private String signature;

}

