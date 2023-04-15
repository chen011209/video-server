package com.yichen.video.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yichen.video.dao.*;
import com.yichen.video.dto.CheckVideoDto;
import com.yichen.video.dto.CollectDto;
import com.yichen.video.dto.UploadDto;
import com.yichen.video.enums.CheckVideoStatusEnum;
import com.yichen.video.enums.ErrorEnum;
import com.yichen.video.model.*;
import com.yichen.video.security.LoginUser;
import com.yichen.video.service.VideoService;
import com.yichen.video.util.FileUtil;
import com.yichen.video.util.NonStaticResourceHttpRequestHandler;
import com.yichen.video.util.UserUtil;
import com.yichen.video.vo.CheckVideoVo;
import com.yichen.video.vo.Result;
import com.yichen.video.vo.VideoVo;
import org.apache.commons.beanutils.BeanUtils;
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
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service("VideoService")
public class VideoServiceImpl implements VideoService {
	@Autowired
	private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;


	@Autowired
	private VideoMapper videoMapper;

	@Autowired
	private CheckVideoMapper checkVideoMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	CollectMapper collectMapper;

	@Autowired
	HistoryMapper historyMapper;

	@Value("${resource.video-path}")
	private String videoPath;

	@Value("${resource.picture-path}")
	private String picturePath;

	@Value("${resource.upload-path}")
	private String uploadPath;


//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

	@Override
	public void getVideo(HttpServletRequest request, HttpServletResponse response, String videoPath) throws Exception {
//        String realPath = videoPath + videoId +".mp4";

		Path filePath = Paths.get(videoPath);
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
		File folder = new File(uploadPath + '\\' + userid.toString());
		if (!folder.isDirectory()) {
			folder.mkdirs();
		}


		String oldName = file.getOriginalFilename();

		String newName = System.currentTimeMillis() + oldName.substring(oldName.lastIndexOf("."));
		try {
			// 文件保存
			file.transferTo(new File(folder, newName));

			// 返回上传文件的访问路径

			return Result.ok(uploadPath + '\\' + userid.toString() + '\\' + newName);
		} catch (IOException e) {
			return Result.fail(ErrorEnum.UPLOAD_FAIL.getCode(), e.getMessage());
		}

	}

	@Override
	public Result uploadVideo(UploadDto uploadDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();

		User user = loginUser.getUser();

		Long userid = user.getUserId();


		File videoFolder = new File(videoPath + '\\' + userid.toString());
		if (!videoFolder.isDirectory()) {
			videoFolder.mkdirs();
		}

		File pictureFolder = new File(picturePath + '\\' + userid.toString());
		if (!pictureFolder.isDirectory()) {
			pictureFolder.mkdirs();
		}


		Long creTime = System.currentTimeMillis();


		String videoSavePth;
		String pictureSavePth;

		try {
			videoSavePth = videoPath + '\\' + userid.toString() + '\\' + creTime + uploadDto.getVideoPath().substring(uploadDto.getVideoPath().lastIndexOf("."));
			pictureSavePth = picturePath + '\\' + userid.toString() + '\\' + creTime + uploadDto.getPicturePath().substring(uploadDto.getPicturePath().lastIndexOf("."));

		} catch (Exception e) {
			return Result.fail(ErrorEnum.FILE_ERROR.getCode(), "文件错误，无法找到指定文件");
		}


		try (InputStream videoInputStream = new FileInputStream(uploadDto.getVideoPath());
			 OutputStream videoOutputStream = new FileOutputStream(videoSavePth);
			 InputStream pictureInputStream = new FileInputStream(uploadDto.getPicturePath());
			 OutputStream pictureOutputStream = new FileOutputStream(pictureSavePth);) {


			byte[] buffer = new byte[1024];
			int length;
			while ((length = videoInputStream.read(buffer)) > 0) {
				videoOutputStream.write(buffer, 0, length);
			}

			while ((length = pictureInputStream.read(buffer)) > 0) {
				pictureOutputStream.write(buffer, 0, length);
			}


		} catch (Exception e) {
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
		} catch (Exception e) {

			e.printStackTrace();

			return Result.fail(ErrorEnum.SQL_ERROR.getCode(), "插入数据库失败");
		}

		return Result.ok();
	}

	@Override
	public Result getCheckVideoList(Integer len) {

		List<CheckVideoVo> checkVideoArrayList = checkVideoMapper.getCheckVideoList(len);


		for (CheckVideoVo checkVideoVo : checkVideoArrayList) {
			checkVideoVo.setPicturePath("http://127.0.0.1:8080/video/picture?picturePath=" + checkVideoVo.getPicturePath().replace('\\', '/'));
//            checkVideoVo.setVideoPath("http://127.0.0.1:8080/video/video?videoPath="+checkVideoVo.getVideoPath().replace('\\','/'));
		}


		return Result.ok(checkVideoArrayList);
	}

	@Override
	public void getPicture(HttpServletResponse response, String picturePath) {
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Result checkVideo(CheckVideoDto checkVideoDto) {

		//更新checkVideo表


		if (checkVideoDto.getStatus() != CheckVideoStatusEnum.CHECKED.getCode()
				&& checkVideoDto.getStatus() != CheckVideoStatusEnum.FAIL.getCode()) {
			return Result.fail(ErrorEnum.ERROR_CHECK_STATUS.getCode(), "审核状态错误");
		}

		Long sysTime = System.currentTimeMillis();

		CheckVideo checkVideo = new CheckVideo();
		checkVideo.setCheckVideoId(checkVideoDto.getCheckVideoId());
		checkVideo.setStatus(checkVideoDto.getStatus().byteValue());
		checkVideo.setCheckTime(sysTime);
		checkVideo.setRemark(checkVideoDto.getRemark());


		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();

		User checkUser = loginUser.getUser();


		checkVideo.setCheckAdminId(checkUser.getUserId());


//        CheckVideoExample checkVideoExample = new CheckVideoExample();
//        checkVideoExample.createCriteria().andCheckVideoIdEqualTo(checkVideoDto.getCheckVideoId());


		checkVideoMapper.updateByPrimaryKeySelective(checkVideo);

//        checkVideoMapper.updateByExample(checkVideo, checkVideoExample);


		//插入一条新的video记录

		CheckVideo checkVideoInfo = checkVideoMapper.selectByPrimaryKey(checkVideoDto.getCheckVideoId());


		Video video = new Video();
		video.setCheckVideoId(checkVideoInfo.getCheckVideoId());
		video.setVideoTitle(checkVideoInfo.getCheckVideoTitle());
		video.setVideoIntroduction(checkVideoInfo.getCheckVideoIntroduction());
		video.setUserId(checkVideoInfo.getUserId());
		video.setVideoPath(checkVideoInfo.getVideoPath());
		video.setPicturePath(checkVideoInfo.getPicturePath());
//        video.setDownvoteNum(0);
//        video.setPraiseNum(0);
		video.setReleaseTime(sysTime);
		video.setViewNum(0);

		videoMapper.insert(video);

		return Result.ok();
	}

	@Override
	public Result getCheckVideoInfo(Long checkVideoId) {

		CheckVideo checkVideo = checkVideoMapper.selectByPrimaryKey(checkVideoId);


		//将数据库中的本地路径 转为可以直接通过接口访问的路径
		checkVideo.setPicturePath("http://127.0.0.1:8080/video/picture?picturePath=" + checkVideo.getPicturePath().replace('\\', '/'));
		checkVideo.setVideoPath("http://127.0.0.1:8080/video/video?videoPath=" + checkVideo.getVideoPath().replace('\\', '/'));

		return Result.ok(checkVideo);
	}

	@Override
	public Result getVideoInfo(Long videoId) {
		Long userId = UserUtil.getUserId();


		Video video = videoMapper.selectByPrimaryKey(videoId);


		video.setPicturePath("http://127.0.0.1:8080/video/picture?picturePath=" + video.getPicturePath().replace('\\', '/'));
		video.setVideoPath("http://127.0.0.1:8080/video/video?videoPath=" + video.getVideoPath().replace('\\', '/'));


		//视频观看数量++ 用户经验值+5
		videoMapper.increaseViewNum(videoId);



		HistoryExample historyExample = new HistoryExample();
		historyExample.createCriteria()
				.andVideoIdEqualTo(videoId)
				.andUserIdEqualTo(userId);

		List<History> historyList = historyMapper.selectByExample(historyExample);


		if(historyList.size()==0){
			History history = new History();
			history.setUserId(userId);
			history.setViewTime(System.currentTimeMillis());
			history.setVideoId(videoId);

			historyMapper.insert(history);
		}else {
			historyList.get(0).setViewTime(System.currentTimeMillis());
			historyMapper.updateByPrimaryKey(historyList.get(0));
		}






		User user = userMapper.selectByPrimaryKey(userId);


		if (user.getUserLevel() == 7) {
			return Result.ok(video);
		} else {
			user.setUserLevelPoints(user.getUserLevelPoints() + 5);

			Integer max = UserUtil.getMaxLevelPoints(Integer.valueOf(user.getUserLevel()));

			if (user.getUserLevelPoints() >= max) {
				user.setUserLevelPoints(user.getUserLevelPoints() - max);
				user.setUserLevel((byte) (user.getUserLevel() + 1));
			}

			userMapper.updateByPrimaryKeySelective(user);
		}

		return Result.ok(video);

	}

	@Override
	public PageInfo<VideoVo> getPopularVideoList(int pageNum, int pageSize) {


		PageHelper.startPage(pageNum, pageSize);
		// 调用Mapper接口方法


		List<VideoVo> videoVoList = videoMapper.getVideoList();

		for (VideoVo videoVo : videoVoList) {
			videoVo.setPicturePath("http://127.0.0.1:8080/video/picture?picturePath=" + videoVo.getPicturePath().replace('\\', '/'));

		}


		return new PageInfo<>(videoVoList);

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


	@Override
	public Result collectVideo(CollectDto collectDto) {
		CollectExample collectExample = new CollectExample();
		collectExample.createCriteria()
				.andVideoIdEqualTo(collectDto.getVideoId())
				.andUserIdEqualTo(UserUtil.getUserId());

		List<Collect> collectList = collectMapper.selectByExample(collectExample);

		if(collectList.size()!=0){
			return Result.fail(ErrorEnum.COLLECT_REPEAT.getCode(),"已收藏过该视频");
		}

		Collect collect = new Collect();
		collect.setVideoId(collectDto.getVideoId());
		collect.setUserId(UserUtil.getUserId());
		collect.setTime(System.currentTimeMillis());
		collectMapper.insert(collect);

		return Result.ok();
	}

	@Override
	public PageInfo<VideoVo> getCollectList(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);


		Long userId = UserUtil.getUserId();


		CollectExample collectExample = new CollectExample();
		collectExample.createCriteria()
				.andUserIdEqualTo(userId);
		List<Collect> collectList = collectMapper.selectByExample(collectExample);

		List<VideoVo> videoVoList = new ArrayList<>(collectList.size());

//        Collect collect = collectMapper.selectByPrimaryKey(userId);


		PageInfo result = new PageInfo<>(collectList);


		for (Collect collect : collectList) {
			Video video = videoMapper.selectByPrimaryKey(collect.getVideoId());
			VideoVo videoVo = new VideoVo();
			videoVo.setUserName(userMapper.selectByPrimaryKey(video.getUserId()).getUserName());

			try {
				BeanUtils.copyProperties(videoVo, video);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}


			videoVo.setPicturePath(FileUtil.getPicturePath(videoVo.getPicturePath()));

			videoVoList.add(videoVo);

		}


		result.setList(videoVoList);

        return result;
	}

	@Override
	public PageInfo<VideoVo> getHistoryList(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Long userId = UserUtil.getUserId();


		HistoryExample historyExample = new HistoryExample();
		historyExample.createCriteria()
				.andUserIdEqualTo(userId);
		List<History> historyList = historyMapper.selectByExample(historyExample);

		List<VideoVo> videoVoList = new ArrayList<>(historyList.size());

		PageInfo result = new PageInfo<>(historyList);


		for (History history : historyList) {
			Video video = videoMapper.selectByPrimaryKey(history.getVideoId());
			VideoVo videoVo = new VideoVo();
			videoVo.setUserName(userMapper.selectByPrimaryKey(video.getUserId()).getUserName());

			try {
				BeanUtils.copyProperties(videoVo, video);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}


			videoVo.setPicturePath(FileUtil.getPicturePath(videoVo.getPicturePath()));

			videoVoList.add(videoVo);

		}


		result.setList(videoVoList);

		return result;
	}

	@Override
	public PageInfo<VideoVo> getSearchList(int pageNum, int pageSize, String content) {

		PageHelper.startPage(pageNum, pageSize);


		VideoExample videoExample = new VideoExample();
		videoExample.createCriteria()
				.andVideoTitleLike("%"+content+"%");


		List<Video> videoList = videoMapper.selectByExample(videoExample);




		List<VideoVo> videoVoList = new ArrayList<>(videoList.size());

		PageInfo result = new PageInfo<>(videoList);


		for (Video video : videoList) {

			VideoVo videoVo = new VideoVo();
			videoVo.setUserName(userMapper.selectByPrimaryKey(video.getUserId()).getUserName());

			try {
				BeanUtils.copyProperties(videoVo, video);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}


			videoVo.setPicturePath(FileUtil.getPicturePath(videoVo.getPicturePath()));

			videoVoList.add(videoVo);

		}


		result.setList(videoVoList);

		return result;
	}

	@Override
	public PageInfo<VideoVo> getVideoList(int pageNum, int pageSize, Long userId) {
		PageHelper.startPage(pageNum, pageSize);


		VideoExample videoExample = new VideoExample();
		videoExample.createCriteria()
				.andUserIdEqualTo(userId);


		List<Video> videoList = videoMapper.selectByExample(videoExample);



		List<VideoVo> videoVoList = new ArrayList<>(videoList.size());

		PageInfo result = new PageInfo<>(videoList);




		for (Video video : videoList) {

			VideoVo videoVo = new VideoVo();

			try {
				BeanUtils.copyProperties(videoVo, video);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}


			videoVo.setPicturePath(FileUtil.getPicturePath(videoVo.getPicturePath()));

			videoVoList.add(videoVo);

		}


		result.setList(videoVoList);

		return result;
	}

	@Override
	public PageInfo<VideoVo> getUserVideoList(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);


		VideoExample videoExample = new VideoExample();
		videoExample.createCriteria()
				.andUserIdEqualTo(UserUtil.getUserId());


		List<Video> videoList = videoMapper.selectByExample(videoExample);



		List<VideoVo> videoVoList = new ArrayList<>(videoList.size());

		PageInfo result = new PageInfo<>(videoList);




		for (Video video : videoList) {

			VideoVo videoVo = new VideoVo();

			try {
				BeanUtils.copyProperties(videoVo, video);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}


			videoVo.setPicturePath(FileUtil.getPicturePath(videoVo.getPicturePath()));

			videoVoList.add(videoVo);

		}


		result.setList(videoVoList);

		return result;

	}


}
