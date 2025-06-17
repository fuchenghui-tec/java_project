package com.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTTest {
    /**
     * 测试生成jwt令牌
     */
    @Test
    public void testGenerateJwt(){
        //自定义信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "Tom");

        //生成jwt令牌
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "itheima")
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 12*3600*1000)) //设置过期时间，什么时候令牌失效 (2小时后失效)
                // .setExpiration(new Date(12*3600*1000)) //设置过期时间，什么时候令牌失效 (2小时后失效)
                .compact();

        //eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJUb20iLCJleHAiOjE3MTQwNTcwMDF9.MI5adjoKWquLmQdiWkowAphQpMSTQyjoNqDk5q3r51o
        //eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJUb20iLCJleHAiOjQzMjAwfQ.sKohs3XE8RoiWtGgQtjwYVslhygqAwLBywZi5Z9gWc4
        System.out.println(jwt);
    }

    /**
     * jwt令牌校验
     * 校验失败有两种情况：
     *  1.令牌被篡改
     *  2.令牌过期了
     */
    @Test
    public void testParseJwt(){
        Claims claims = Jwts.parser().setSigningKey("itheima")
                // .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJUb20iLCJleHAiOjE3MTQwNTcwMDF9.MI5adjoKWquLmQdiWkowAphQpMSTQyjoNqDk5q3r51o")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJUb20iLCJleHAiOjQzMjAwfQ.sKohs3XE8RoiWtGgQtjwYVslhygqAwLBywZi5Z9gWc4")
                .getBody();

        System.out.println(claims);
    }
}
