package com.yichen.video.enums;

import lombok.Getter;


@Getter
public enum ErrorEnum {
    LOGIN_FAILED(1),
    REGISTER_FAILED(2)
    ;

    private Integer code;


    ErrorEnum(Integer code) {
        this.code = code;
    }
}


