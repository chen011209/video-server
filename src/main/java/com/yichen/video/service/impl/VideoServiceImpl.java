package com.yichen.video.service.impl;

import com.yichen.video.dao.CheckVideoMapper;
import com.yichen.video.dto.UploadDto;
import com.yichen.video.enums.CheckVideoStatusEnum;
import com.yichen.video.enums.ErrorEnum;
import com.yichen.video.enums.UserTypeEnum;
import com.yichen.video.model.CheckVideo;
import com.yichen.video.model.CheckVideoExample;
import com.yichen.video.model.User;
import com.yichen.video.model.UserExample;
import com.yichen.video.security.LoginUser;
import com.yichen.video.service.VideoService;
import com.yichen.video.util.NonStaticResourceHttpRequestHandler;
import com.yichen.video.vo.CheckVideoVo;
import com.yichen.video.vo.Result;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("VideoService")
public class VideoServiceImpl implements VideoService {
    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;



    @Autowired
    private CheckVideoMapper checkVideoMapper;

    @Value("${resource.video-path}")
    private String videoPath;

    @Value("${resource.picture-path}")
    private String picturePath;

    @Value("${resource.upload-path}")
    private String uploadPath;


//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    @Override
    public void getVideo(HttpServletRequest request, HttpServletResponse response, String videoPath) throws Exception{
//        String realPath = videoPath + videoId +".mp4";

        Path filePath = Paths.get(videoPath );
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

    @Override
    public Result uploadVideo(UploadDto uploadDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        User user = loginUser.getUser();

        Long userid = user.getUserId();


        File videoFolder = new File(videoPath+'\\'+userid.toString());
        if (!videoFolder.isDirectory()) {
            videoFolder.mkdirs();
        }

        File pictureFolder = new File(picturePath+'\\'+userid.toString());
        if (!pictureFolder.isDirectory()) {
            pictureFolder.mkdirs();
        }


        Long creTime = System.currentTimeMillis();


        String videoSavePth;
        String pictureSavePth;

        try {
            videoSavePth = videoPath +'\\'+userid.toString() + '\\' + creTime + uploadDto.getVideoPath().substring(uploadDto.getVideoPath().lastIndexOf("."));
            pictureSavePth = picturePath +'\\'+userid.toString() + '\\' + creTime + uploadDto.getPicturePath().substring(uploadDto.getPicturePath().lastIndexOf("."));

        }catch (Exception e){
            return Result.fail(ErrorEnum.FILE_ERROR.getCode(), "文件错误，无法找到指定文件");
        }





        try(InputStream videoInputStream = new FileInputStream(uploadDto.getVideoPath());
            OutputStream videoOutputStream = new FileOutputStream(videoSavePth);
            InputStream pictureInputStream = new FileInputStream(uploadDto.getPicturePath());
            OutputStream pictureOutputStream = new FileOutputStream(pictureSavePth);){


            byte[] buffer = new byte[1024];
            int length;
            while ((length = videoInputStream.read(buffer)) > 0) {
                videoOutputStream.write(buffer, 0, length);
            }

            while ((length = pictureInputStream.read(buffer)) > 0) {
                pictureOutputStream.write(buffer, 0, length);
            }




        }catch (Exception e){
            return Result.fail(ErrorEnum.FILE_ERROR.getCode(), "文件错误，无法找到指定文件");
        }




        try {


            CheckVideo checkVideo = new CheckVideo();
            checkVideo.setCheckVideoTitle(uploadDto.getTitle());
            checkVideo.setCheckVideoIntroduction(uploadDto.getIntroduction());
            checkVideo.setVideoPath(videoSavePth);
            checkVideo.setPicturePath(pictureSavePth);
            checkVideo.setUserId(userid);
            checkVideo.setUploadTime(creTime);
            checkVideo.setStatus(CheckVideoStatusEnum.PRE_CHECK.getCode().byteValue());


            checkVideoMapper.insert(checkVideo);
        }catch (Exception e){

            e.printStackTrace();

            return Result.fail(ErrorEnum.SQL_ERROR.getCode(), "插入数据库失败");
        }

        return Result.ok();
    }

    @Override
    public Result getCheckVideoList() {

        List<CheckVideoVo> checkVideoArrayList = checkVideoMapper.getCheckVideoList(16);


        for(CheckVideoVo checkVideoVo:checkVideoArrayList){
            checkVideoVo.setPicturePath("http://127.0.0.1:8080/video/picture?picturePath="+checkVideoVo.getPicturePath().replace('\\','/'));
            checkVideoVo.setVideoPath("http://127.0.0.1:8080/video/video?videoPath="+checkVideoVo.getVideoPath().replace('\\','/'));
        }


        return Result.ok(checkVideoArrayList);
    }

    @Override
    public void getPicture(HttpServletResponse response, String picturePath) {
        try{
            //        File file = new File("D:\\bishe\\video-repository\\picture\\39\\1678626562935.jpg");
            File file = new File(picturePath);
            BufferedImage image = ImageIO.read(file);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] imageData = baos.toByteArray();

            response.setContentType("image/jpeg");
            response.setContentLength(imageData.length);
            response.getOutputStream().write(imageData);
            response.getOutputStream().flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


//        File folder = new File(uploadPath+'\\'+userid.toString());
//        if (!folder.isDirectory()) {
//            folder.mkdirs();
//        }
//
//
//        String oldName = file.getOriginalFilename();
//
//        String newName = System.currentTimeMillis() + oldName.substring(oldName.lastIndexOf("."));
//        try {
//            // 文件保存
//            file.transferTo(new File(folder, newName));
//
//            // 返回上传文件的访问路径
//
//            return Result.ok(uploadPath + '\\'+userid.toString() +'\\' + newName);
//        } catch (IOException e) {
//            return Result.fail(ErrorEnum.UPLOAD_FAIL.getCode(),e.getMessage());
//        }








}
