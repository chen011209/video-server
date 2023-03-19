package com.yichen.video.enums;

import lombok.Getter;


@Getter
public enum CheckVideoStatusEnum {
    //0待审核 1审核通过 2审核不通过
    PRE_CHECK(0),
    CHECKED(1),
    FAIL(2)
    ;

    private Integer code;


    CheckVideoStatusEnum(Integer code) {
        this.code = code;
    }
}