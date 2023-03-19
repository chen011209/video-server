package com.yichen.video.enums;

import lombok.Getter;


@Getter
public enum UserTypeEnum {
    USER(1),
    ADMIN(0)
    ;

    private Integer code;


    UserTypeEnum(Integer code) {
        this.code = code;
    }
}


