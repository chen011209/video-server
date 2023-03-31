package com.yichen.video.enums;

import lombok.Getter;


@Getter
public enum ErrorEnum {
    LOGIN_FAILED(1),
    REGISTER_FAILED(2),
    UPLOAD_FAIL(3),
    FILE_ERROR(4),
    SQL_ERROR(5),
    ERROR_CHECK_STATUS(6),
    VERIFICATION_CODE_ERROR(7),
    MAIL_ERROR(8),
    LIKE_REPEAT(9),
    COLLECT_REPEAT(10)
    ;

    private Integer code;


    ErrorEnum(Integer code) {
        this.code = code;
    }
}


