package com.yichen.video.controller;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.yichen.video.dto.CheckVideoDto;
import com.yichen.video.dto.CollectDto;
import com.yichen.video.dto.ScoreVideoDto;
import com.yichen.video.dto.UploadDto;
import com.yichen.video.model.Comment;
import com.yichen.video.service.RecommendService;
import com.yichen.video.service.ScoreService;
import com.yichen.video.service.UserService;
import com.yichen.video.service.VideoService;
import com.yichen.video.vo.Result;
import com.yichen.video.vo.VideoVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;


@RestController
@RequestMapping("/video")
@CrossOrigin
public class VideoController {


    @Autowired
    private VideoService videoService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScoreService scoreService;

    /**
     * 上传一个视频所有的信息 包括标题、简介
     * @param request
     * @param response
     * @param uploadDto
     * @return
     */
    @PostMapping("/upload")
    public Result postVideo(HttpServletRequest request, HttpServletResponse response
            , @RequestBody UploadDto uploadDto){


        return videoService.uploadVideo(uploadDto);
    }


    /**
     * 上传文件
     * @param request
     * @param response
     * @param file
     * @return
     */
    @PostMapping("/uploadfile")
    public Result uploadFile(HttpServletRequest request, HttpServletResponse response
            , @RequestParam("file") MultipartFile file
    ){
//        return Result.ok(file.getSize());
        return videoService.uploadFile(file);

    }


//    @GetMapping("/videoList")
//    public Result getVideoList(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        return videoService.getCheckVideoList();
//    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/checkVideoList")
    public Result getCheckVideoList(HttpServletRequest request, HttpServletResponse response,@RequestParam Integer len) throws Exception{
        return videoService.getCheckVideoList(len);
    }



    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/check")
    public Result checkVideo(HttpServletRequest request, HttpServletResponse response, @RequestBody CheckVideoDto checkVideoDto){
        return videoService.checkVideo(checkVideoDto);
    }



    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/check/videoInfo")
    public Result getCheckVideoInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam Long checkVideoId){
        return videoService.getCheckVideoInfo(checkVideoId);
    }

    // TODO: 2023/3/14 获取下个视频不能和现在视频重复 
//    @GetMapping("/checkVideoList/next")
//    public Result getNextCheckVideo(HttpServletRequest request, HttpServletResponse response,@RequestParam Long len) throws Exception{
//        return videoService.getCheckVideoList(len);
//    }

    @GetMapping("/videoInfo")
    public Result getVideoInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam Long videoId){
        return videoService.getVideoInfo(videoId);
    }



    @GetMapping("/video")
    public void getVideo(HttpServletRequest request, HttpServletResponse response,@RequestParam String videoPath) throws Exception{
        videoService.getVideo(request,response,videoPath);
    }


    @GetMapping("/picture")
    public void getPicture(HttpServletRequest request, HttpServletResponse response,@RequestParam String picturePath) throws Exception{

//        http://localhost:8080/video/picture?picturePath=D:/bishe//video-repository/picture/39/1678626562935.jpg

        videoService.getPicture(response,picturePath);

    }


    // TODO: 2023/3/14 定期清理审核不通过的视频  将审核通过的checkVideo表留着 将审核不通过的数据库和文件都删除
    @GetMapping("/list/popular")
    public PageInfo<VideoVo> getPopularVideoList(
            HttpServletRequest request, HttpServletResponse response
            , @RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "10") int pageSize) {
        return videoService.getPopularVideoList(pageNum,pageSize);
    }



    @GetMapping("/score")
    public Result getScore(HttpServletRequest request, HttpServletResponse response, @RequestParam Long  videoId) throws Exception{
        return scoreService.getScore(videoId);
//        return null;
    }


    @GetMapping("/user/info")
    public Result getIndividualInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam Long  videoId) throws Exception{
        return userService.getIndividualInfo(videoId);
//        return null;
    }

    @PostMapping("/score")
    public Result scoreVideo(HttpServletRequest request, HttpServletResponse response, @RequestBody ScoreVideoDto scoreVideoDto) throws Exception{
        return scoreService.scoreVideo(scoreVideoDto);
//        return null;
    }


    @GetMapping("/list/recommend")
    public Result getVideoList(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        videoService.getVideo(request,response,videoPath);
        return recommendService.getRecommend();
    }



    @PostMapping("/collect")
    public Result collectVideo(HttpServletRequest request, HttpServletResponse response,@RequestBody CollectDto collectDto) throws Exception{
//        videoService.getVideo(request,response,videoPath);
        return videoService.collectVideo(collectDto);
    }

    @GetMapping("/collect")
    public PageInfo<VideoVo> getCollectList(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "10") int pageSize){
        return videoService.getCollectList(pageNum,pageSize);

    }

    @GetMapping("/history")
    public PageInfo<VideoVo> getHistoryList(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "10") int pageSize){
        return videoService.getHistoryList(pageNum,pageSize);

    }

    @GetMapping("/search")
    public PageInfo<VideoVo> searchVideo(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "10") int pageSize,@RequestParam  String content){
        return videoService.getSearchList(pageNum,pageSize,content);
    }

    /**
     * 获取用户上传的视频
     * @param request
     * @param response
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list/my")
    public PageInfo<VideoVo> getUserVideoList(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "10") int pageSize){
        return videoService.getUserVideoList(pageNum,pageSize);
    }


    @GetMapping("/list")
    public PageInfo<VideoVo> getVideoList(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "10") int pageSize
    ,@RequestParam Long userId){
        return videoService.getVideoList(pageNum,pageSize,userId);
    }
}
