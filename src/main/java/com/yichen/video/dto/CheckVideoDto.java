package com.yichen.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckVideoDto implements Serializable {
    private Long checkVideoId;
    private Integer status;
    private String remark;
}
