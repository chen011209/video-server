package com.yichen.video.util;

import com.yichen.video.model.User;
import com.yichen.video.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public static Long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User user = loginUser.getUser();
        Long userid = user.getUserId();

        return userid;
    }


    public static Integer getMaxLevelPoints(Integer level){
        switch (level){
            case 1:
                return 200;
            case 2:
                return 400;
            case 3:
                return 800;
            case 4:
                return 1600;
            case 5:
                return 3200;
            case 6:
                return 6400;
            default:
                return -1;
        }
    }
}
