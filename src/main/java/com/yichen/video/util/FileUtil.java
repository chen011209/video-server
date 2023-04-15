package com.yichen.video.util;

import com.yichen.video.model.User;
import com.yichen.video.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class FileUtil {
    public static String getPicturePath(String picturePath){
        return "http://127.0.0.1:8080/video/picture?picturePath="+picturePath.replace('\\','/');

    }


}
