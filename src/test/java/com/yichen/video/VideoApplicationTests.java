package com.yichen.video;

import com.yichen.video.dao.*;
import com.yichen.video.enums.UserTypeEnum;
import com.yichen.video.model.*;
import com.yichen.video.util.UserUtil;
import com.yichen.video.util.sensitiveWord.SensitivewordFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

@SpringBootTest
class VideoApplicationTests {

//    @Test
//    void contextLoads() {
//
//
//        try {
//            InputStream inputStream = new FileInputStream("inputFile");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void testBCryptPasswordEncoder() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        /**
//         * encode()：加密方法，传入一个明文，加密后返回一个密文
//         * 同一明文，每次调用encode()方法生成出来的密文都是不一样的，
//         * 因为内部进行加密的时候，会生成一个【随机的加密盐】，
//         * 底层是通过【加密盐】和原文进行一系列处理之后再进行加密
//         * 这样的话，虽然明文一样，但是每一次的密文都是不一样的
//         */
//        String encode_pwd_1 = passwordEncoder.encode("4124");
//        String encode_pwd_2 = passwordEncoder.encode("12aesCCdfsef34");
//
//        System.out.println(encode_pwd_1);
//        System.out.println(encode_pwd_2.length());
//
//
//    }
//
//
//    @Test
//    void testBCryptPasswordEncoder2() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        boolean flag_true = passwordEncoder.
//                matches("1234", "$2a$10$xcKRWSAEYH6xRnJkofPAtu.qIERpgZMK5U7wSPbkTxGxzIKTRoFbO");
//
//        boolean flag_false = passwordEncoder.
//                matches("root", "$2a$10$MBcThIW7Tqm9liaBAXooPepAeovbD8XbM1tV3xvHOA6FHaI6vD4hO");
//
//
//        System.out.println(flag_true);
//        System.out.println(flag_false);
//
//
//    }
//
//
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//
//    @Test
//    public void testRedis(){
//        stringRedisTemplate.opsForValue().set("xiaocai", "888");
//        String res = stringRedisTemplate.opsForValue().get("xiaocai");
//        System.out.println(res);
//    }
//
//
//
//    @Autowired
//    public RedisTemplate redisTemplate;
//
//    @Test
//    public void tt(){
//        stringRedisTemplate.opsForValue().set("xiaocai", "888");
//        String res = stringRedisTemplate.opsForValue().get("xiaocai");
//        System.out.println(res);
//    }
//
//
//
//    @Test
//    public void random(){
//        for (int i = 0; i < 200; i++) {
//            int j = (int) (Math.random() * 20 );
//            System.out.println(j);
//        }
//
//
//    }
//
//
//
//    @Autowired
//    ScoreMapper scoreMapper;
//    @Test
//    public void insertScore(){
//
//
//        String path = "D:\\bishe\\CollaborativeFiltering-master\\CollaborativeFiltering-master\\src\\tttt/ml-data_0/u1.base";
//
//
//
//        File inputFile=new File(path);
//        BufferedReader reader=null;
//        try {
//            reader=new BufferedReader(new FileReader(inputFile));
//        } catch (FileNotFoundException e) {
//            System.out.println("文件不存在"+e.getMessage());
//        }
//
//        int i = 0;
//        String sentence="";
//        String[] part=new String[3];
//        try {
//            while((sentence=reader.readLine())!=null){
//                part=sentence.split("\t");
//                Long userID=Long.parseLong(part[0]);
//                Long itemID=Long.parseLong(part[1]);
//                Byte Rate=Byte.parseByte(part[2]);
//                //构造矩阵
//
////                System.out.println(userID);
////                System.out.println(itemID);
////                System.out.println(Rate);
//
//                Score score = new Score();
//                score.setScore(Rate);
//                score.setUserId(userID);
//                score.setVideoId(itemID);
//                score.setScoreTime(System.currentTimeMillis());
//                scoreMapper.insert(score);
//
//                System.out.println(i++);
//
//
//            }
//        } catch (NumberFormatException|IOException e) {
//            System.out.println("读文件发生错误"+e.getMessage());
//        }
//
//
//
//
//    }
//
//    @Autowired
//    UserMapper userMapper;
//    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    @Test
//    public void insertUser(){
//        for (int i = 1; i <= 1000; i++) {
//
//
//            System.out.println(i);
//
//            User user = new User();
//
//            user.setUserName(String.valueOf(i));
//            user.setUserEmail(String.valueOf(i)+"@qq.com");
//            user.setSignature("我是"+String.valueOf(i)+"号用户");
//            user.setType(UserTypeEnum.USER.getCode().byteValue());
//            user.setUserLevel((byte) 0);
//            user.setUserLevelPoints(0);
//            user.setPassword(passwordEncoder.encode(String.valueOf(i)));
//            user.setCreTime(System.currentTimeMillis());
//
//
//            userMapper.insert(user);
//        }
//    }
//
    @Autowired
    VideoMapper videoMapper;




    @Test
	public  void update(){


    	VideoExample videoExample = new VideoExample();
    	videoMapper.selectByExample(videoExample);



	}

    @Test
	public void updateVideo(){

		HashMap videoTitleMap = new HashMap();

		videoTitleMap.put(2,"dark souls");
		videoTitleMap.put(3,"Edge居然能这么离谱");
		videoTitleMap.put(4,"如果白夜选择艾尔文");
		videoTitleMap.put(5,"Edge结合chatGpt");
		videoTitleMap.put(6,"SpaceX公司将发射世界上第一个商业空间站");
		videoTitleMap.put(7,"Windows12粉丝版概念设计宣传片");
		videoTitleMap.put(8,"一线城市人口负增长时代");
		videoTitleMap.put(9,"一个人摩旅");

		HashMap videoIntroductionMap = new HashMap();

		videoIntroductionMap.put(2,"dark souls");
		videoIntroductionMap.put(3,"Edge使用技巧");
		videoIntroductionMap.put(4,"白夜选择艾尔文的一些思考");
		videoIntroductionMap.put(5,"Edge结合chatGpt解锁最强浏览器");
		videoIntroductionMap.put(6,"SpaceX发射商业空间站");
		videoIntroductionMap.put(7,"Windows12粉丝版概念设计宣传片：任务栏史上最大升级，桌面交互彻底重");
		videoIntroductionMap.put(8,"见证历史，一线城市人口负增长时代，重磅来袭");
		videoIntroductionMap.put(9,"我永远热爱一个人摩旅在路上的自由与无拘，这些经历将会是我未来沉闷生活岁月里的解药。");

    	Video video = new Video();
		for (int i = 1; i < 1699; i++) {

			int random;
			Random random1 = new Random();

//			2到9
			random = Math.abs(random1.nextInt()%8)+2;
			video.setVideoId(Long.parseLong(String.valueOf(i)));



			video.setPicturePath("D:\\bishe\\video-repository\\picture\\1000\\168465491406"+random+".jpg");
			video.setVideoPath("D:\\bishe\\video-repository\\video\\1000\\168465491406"+random+".mp4");



			video.setVideoTitle("视频("+i+") "+videoTitleMap.get(random));
			video.setVideoIntroduction((String) videoIntroductionMap.get(random));

			videoMapper.updateByPrimaryKeySelective(video);

		}


	}





//    @Test
//    public void insertVideo(){
//
//        Video video = new Video();
//        video.setCheckVideoId(1L);
//
//        for (int i = 1; i < 1700; i++) {
//
//            if(i%3 ==0){
//                video.setVideoTitle("(视频id:"+i+")别再用谷歌了！7分钟让你爱上Edge浏览器！");
//                video.setVideoIntroduction("兄弟们！edge 我真的爱上她了！");
//
//                video.setVideoPath("D:\\bishe\\video-repository\\video\\1854\\1679197142072.mp4");
//                video.setPicturePath("D:\\bishe\\video-repository\\picture\\1854\\1679197142072.jpg");
//            }else if(i%3 == 1){
//                video.setVideoTitle("(视频id:"+i+")Chat GPT+mid journey 十秒钟做插画，这也太爽了");
//                video.setVideoIntroduction("-");
//
//                video.setVideoPath("D:\\bishe\\video-repository\\video\\1854\\1679197200263.mp4");
//                video.setPicturePath("D:\\bishe\\video-repository\\picture\\1854\\1679197200263.jpg");
//
//            }else if(i %3==2){
//                video.setVideoTitle("(视频id:"+i+")如果选择了埃尔文，结局会如何？");
//                video.setVideoIntroduction("如果选择了埃尔文，结局会如何走向？小伙伴们可以在评论区畅所欲言");
//
//                video.setVideoPath("D:\\bishe\\video-repository\\video\\1854\\1679197268508.mp4");
//                video.setPicturePath("D:\\bishe\\video-repository\\picture\\1854\\1679197268508.jpg");
//
//            }
//
//
//
//
//            video.setUserId(1000L);
//            video.setReleaseTime(System.currentTimeMillis());
//            video.setViewNum(0);
//
//            videoMapper.insert(video);
//        }
//
//
//
//    }
//
//
//    @Autowired
//    CommentMapper commentMapper;
//    @Test
//    public void insertComment(){
//
//        for (int i = 333; i < 555; i++) {
//            Comment comment = new Comment();
//            comment.setContent(String.valueOf(i));
//            comment.setLikes(0);
//            comment.setTime(System.currentTimeMillis());
//            comment.setVideoId(98L);
//            comment.setUserId(1L);
//            commentMapper.insert(comment);
//        }
//
//    }
//
//    @Autowired
//    SensitivewordFilter filter;
//    @Test
//    public void testSensitive(){
////        SensitivewordFilter filter = new SensitivewordFilter();
////        System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());
//
//        String string = "支持西藏独立太多的伤感sb情怀也许只局限于饲养基地 荧幕中的情节。"
//                + "然后 我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
//                + "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个贱人一杯红酒一部电影在夜 深人静的晚上，关上电话静静的发呆着。西藏独立";
//        System.out.println("待检测语句字数：" + string.length());
//
//        long beginTime = System.currentTimeMillis();
////        Set<String> set = filter.getSensitiveWord(string, 1);
//        String txt=filter.replaceSensitiveWord(string,1,"*");
//        long endTime = System.currentTimeMillis();
//
////        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
//        System.out.println(txt);
//        System.out.println("总共消耗时间为：" + (endTime - beginTime));
//    }
//
//
//    @Autowired
//    FollowMapper followMapper;
//    @Test
//    public void addFollow(){
//        Follow follow = new Follow();
//        follow.setUserId(1L);
//        for (int i = 0; i < 100; i++) {
//            follow.setFollowUserId(Long.parseLong(String.valueOf(i)));
//            follow.setFollowTime(System.currentTimeMillis());
//            followMapper.insert(follow);
//        }
//    }

}
