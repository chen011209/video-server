package com.yichen.video.service.impl;


import com.yichen.video.dao.ScoreMapper;
import com.yichen.video.dto.ScoreVideoDto;
import com.yichen.video.model.Score;
import com.yichen.video.model.ScoreExample;
import com.yichen.video.model.User;
import com.yichen.video.security.LoginUser;
import com.yichen.video.service.ScoreService;
import com.yichen.video.util.UserUtil;
import com.yichen.video.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ScoreService")
public class ScoreServiceImpl implements ScoreService {
	@Autowired
	ScoreMapper scoreMapper;


	@Override
	public Result getScore(Long videoId) {
		ScoreExample scoreExample = new ScoreExample();
		scoreExample.createCriteria()
				.andVideoIdEqualTo(videoId)
				.andUserIdEqualTo(UserUtil.getUserId());

		List<Score> scoreList = scoreMapper.selectByExample(scoreExample);

		if(scoreList.size()!=0){
			return Result.ok(scoreList.get(0));
		}else {
			return Result.ok();
		}

	}

	@Override
	public Result scoreVideo(ScoreVideoDto scoreVideoDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		User user = loginUser.getUser();
		Long userid = user.getUserId();


		ScoreExample scoreExample = new ScoreExample();
		scoreExample.createCriteria()
				.andUserIdEqualTo(userid)
				.andVideoIdEqualTo(scoreVideoDto.getVideoId());

		List<Score> scoreList = scoreMapper.selectByExample(scoreExample);


		Score score = new Score();
		score.setScore(scoreVideoDto.getScore().byteValue());
		score.setUserId(userid);
		score.setVideoId(scoreVideoDto.getVideoId());
		score.setScoreTime(System.currentTimeMillis());

		if(scoreList.size()==0){
			scoreMapper.insert(score);
		}else {
			score.setScoreId(scoreList.get(0).getScoreId());
			scoreMapper.updateByPrimaryKey(score);
		}
		return Result.ok();

	}
}
