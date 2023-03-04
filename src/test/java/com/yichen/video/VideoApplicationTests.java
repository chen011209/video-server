package com.yichen.video;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@SpringBootTest
class VideoApplicationTests {

    @Test
    void contextLoads() {
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

}
