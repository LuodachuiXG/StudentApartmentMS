package com.example.studentapartmentms;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest
class StudentApartmentMsApplicationTests {

    /**
     * 生成 Token
     */
    @Test
    void tokenCreate() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, 20);
        String token = JWT.create()
                .withClaim("userId", 21)
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256("secret"));
        System.out.println(token);
    }

    /**
     * 验证 Token
     */
    @Test
    void tokenVerify() {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("secret")).build();
        DecodedJWT verify = jwtVerifier.verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjIxLCJleHAiOjE3MDYwODA5Mjd9.-cppJ8RhH9sUl2P2-p1GtH-fUj8RCXAQAIzWGIiliJ8");
        System.out.println(verify.getToken());
        System.out.println(verify.getClaim("userId"));
        System.out.println(verify.getExpiresAt());
    }
}
