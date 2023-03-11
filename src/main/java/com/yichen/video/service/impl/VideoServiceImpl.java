package com.yichen.video.service.impl;

import com.yichen.video.enums.ErrorEnum;
import com.yichen.video.enums.UserTypeEnum;
import com.yichen.video.model.User;
import com.yichen.video.security.LoginUser;
import com.yichen.video.service.VideoService;
import com.yichen.video.util.NonStaticResourceHttpRequestHandler;
import com.yichen.video.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("VideoService")
public class VideoServiceImpl implements VideoService {
    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;



    @Value("${resource.video-path}")
    private String videoPath;


    @Value("${resource.upload-path}")
    private String uploadPath;


//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    @Override
    public void getVideo(HttpServletRequest request, HttpServletResponse response, String videoId) throws Exception{
        String realPath = videoPath + videoId +".mp4";

        Path filePath = Paths.get(realPath );
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }

    }

    @Override
    public Result uploadFile(MultipartFile file) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        User user = loginUser.getUser();

        Long userid = user.getUserId();




        // 在 uploadPath 文件夹中通过日期对上传的文件归类保存
        // 比如：/2019/06/06/cf13891e-4b95-4000-81eb-b6d70ae44930.png
//        String format = sdf.format(new Date());


        //存放在用户的上传文件夹中 以用户id为名
        File folder = new File(uploadPath+'\\'+userid.toString());
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }


        String oldName = file.getOriginalFilename();

        String newName = System.currentTimeMillis() + oldName.substring(oldName.lastIndexOf("."));
        try {
            // 文件保存
            file.transferTo(new File(folder, newName));

            // 返回上传文件的访问路径

            return Result.ok(uploadPath + '\\'+userid.toString() +'\\' + newName);
        } catch (IOException e) {
            return Result.fail(ErrorEnum.UPLOAD_FAIL.getCode(),e.getMessage());
        }

    }
}
