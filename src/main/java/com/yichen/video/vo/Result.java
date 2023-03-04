package com.yichen.video.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private Boolean success;
    private Integer errorCode;
    private String msg;
    private Object data;
    private Long total;

    public static Result ok(){
        return new Result(true, null, null, null,null);
    }
    public static Result ok(Object data){
        return new Result(true, null,null , data,null);
    }
    public static Result ok(Object data,String msg){
        return new Result(true, null,msg , data,null);
    }
    public static Result ok(List<?> data, Long total){
        return new Result(true, null,null ,data ,total);
    }
    public static Result fail(Integer errorCode,String msg){
        return new Result(false, errorCode, msg, null,null);
    }

}
