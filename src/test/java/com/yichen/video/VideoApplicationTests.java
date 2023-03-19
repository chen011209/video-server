package com.yichen.video;

import com.yichen.video.dao.ScoreMapper;
import com.yichen.video.dao.UserMapper;
import com.yichen.video.dao.VideoMapper;
import com.yichen.video.enums.UserTypeEnum;
import com.yichen.video.model.Score;
import com.yichen.video.model.User;
import com.yichen.video.model.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.util.logging.Logger;

@SpringBootTest
class VideoApplicationTests {

    @Test
    void contextLoads() {


        try {
            InputStream inputStream = new FileInputStream("inputFile");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        /**
         * encode()：加密方法，传入一个明文，加密后返回一个密文
         * 同一明文，每次调用encode()方法生成出来的密文都是不一样的，
         * 因为内部进行加密的时候，会生成一个【随机的加密盐】，
         * 底层是通过【加密盐】和原文进行一系列处理之后再进行加密
         * 这样的话，虽然明文一样，但是每一次的密文都是不一样的
         */
        String encode_pwd_1 = passwordEncoder.encode("4124");
        String encode_pwd_2 = passwordEncoder.encode("12aesCCdfsef34");

        System.out.println(encode_pwd_1);
        System.out.println(encode_pwd_2.length());


    }


    @Test
    void testBCryptPasswordEncoder2() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean flag_true = passwordEncoder.
                matches("1234", "$2a$10$xcKRWSAEYH6xRnJkofPAtu.qIERpgZMK5U7wSPbkTxGxzIKTRoFbO");

        boolean flag_false = passwordEncoder.
                matches("root", "$2a$10$MBcThIW7Tqm9liaBAXooPepAeovbD8XbM1tV3xvHOA6FHaI6vD4hO");


        System.out.println(flag_true);
        System.out.println(flag_false);


    }



    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void testRedis(){
        stringRedisTemplate.opsForValue().set("xiaocai", "888");
        String res = stringRedisTemplate.opsForValue().get("xiaocai");
        System.out.println(res);
    }



    @Autowired
    public RedisTemplate redisTemplate;

    @Test
    public void tt(){
        stringRedisTemplate.opsForValue().set("xiaocai", "888");
        String res = stringRedisTemplate.opsForValue().get("xiaocai");
        System.out.println(res);
    }



    @Test
    public void random(){
        for (int i = 0; i < 200; i++) {
            int j = (int) (Math.random() * 20 );
            System.out.println(j);
        }


    }



    @Autowired
    ScoreMapper scoreMapper;
    @Test
    public void insertScore(){


        String path = "D:\\bishe\\CollaborativeFiltering-master\\CollaborativeFiltering-master\\src\\tttt/ml-data_0/u1.base";



        File inputFile=new File(path);
        BufferedReader reader=null;
        try {
            reader=new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在"+e.getMessage());
        }

        int i = 0;
        String sentence="";
        String[] part=new String[3];
        try {
            while((sentence=reader.readLine())!=null){
                part=sentence.split("\t");
                Long userID=Long.parseLong(part[0]);
                Long itemID=Long.parseLong(part[1]);
                Byte Rate=Byte.parseByte(part[2]);
                //构造矩阵

//                System.out.println(userID);
//                System.out.println(itemID);
//                System.out.println(Rate);

                Score score = new Score();
                score.setScore(Rate);
                score.setUserId(userID);
                score.setVideoId(itemID);
                score.setScoreTime(System.currentTimeMillis());
                scoreMapper.insert(score);

                System.out.println(i++);


            }
        } catch (NumberFormatException|IOException e) {
            System.out.println("读文件发生错误"+e.getMessage());
        }




    }

    @Autowired
    UserMapper userMapper;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Test
    public void insertUser(){
        for (int i = 10; i < 1000; i++) {


            System.out.println(i);

            User user = new User();

            user.setUserName(String.valueOf(i));
            user.setUserEmail(String.valueOf(i)+"@qq.com");
            user.setSignature("我是"+String.valueOf(i)+"号用户");
            user.setType(UserTypeEnum.USER.getCode().byteValue());
            user.setUserLevel((byte) 0);
            user.setUserLevelPoints(0);
            user.setPassword(passwordEncoder.encode(String.valueOf(i)));
            user.setCreTime(System.currentTimeMillis());


            userMapper.insert(user);
        }
    }

    @Autowired
    VideoMapper videoMapper;
    @Test
    public void insertVideo(){

        Video video = new Video();
        video.setCheckVideoId(1L);

        for (int i = 0; i < 1700; i++) {

            if(i%3 ==0){
                video.setVideoTitle("(视频id:"+i+")别再用谷歌了！7分钟让你爱上Edge浏览器！");
                video.setVideoIntroduction("兄弟们！edge 我真的爱上她了！");

                video.setVideoPath("D:\\bishe\\video-repository\\video\\1854\\1679197142072.mp4");
                video.setPicturePath("D:\\bishe\\video-repository\\picture\\1854\\1679197142072.jpg");
            }else if(i%3 == 1){
                video.setVideoTitle("(视频id:"+i+")Chat GPT+mid journey 十秒钟做插画，这也太爽了");
                video.setVideoIntroduction("-");

                video.setVideoPath("D:\\bishe\\video-repository\\video\\1854\\1679197200263.mp4");
                video.setPicturePath("D:\\bishe\\video-repository\\picture\\1854\\1679197200263.jpg");

            }else if(i %3==2){
                video.setVideoTitle("(视频id:"+i+")如果选择了埃尔文，结局会如何？");
                video.setVideoIntroduction("如果选择了埃尔文，结局会如何走向？小伙伴们可以在评论区畅所欲言");

                video.setVideoPath("D:\\bishe\\video-repository\\video\\1854\\1679197268508.mp4");
                video.setPicturePath("D:\\bishe\\video-repository\\picture\\1854\\1679197268508.jpg");

            }




            video.setUserId(1000L);
            video.setReleaseTime(System.currentTimeMillis());
            video.setViewNum(0);

            videoMapper.insert(video);
        }



    }

}
