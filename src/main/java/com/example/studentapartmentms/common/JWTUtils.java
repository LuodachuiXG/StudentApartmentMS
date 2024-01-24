package com.example.studentapartmentms.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 */
public class JWTUtils {

    /** JWT 秘钥 **/
    private static final String secret = "sams_secret";

    /** JWT 过期时间（分钟） **/
    private static final Integer expiresInMinuteAt = 180;

    /**
     * 生成 Token
     * @param claims 自定义声明
     * @return 生成的 Token 字符串
     */
    public static String generateToken(Map<String, String> claims) {
        // 设置令牌过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, expiresInMinuteAt);

        JWTCreator.Builder builder = JWT.create();

        // 将自定义声明添加到 Token 中
        claims.forEach(builder::withClaim);

        // 签名 Token
        return builder
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(secret));
    }


    /**
     * 验证 Token 合法性
     * @param token 要验证的 Token
     * @return 返回 {@link DecodedJWT} 实体类
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build().verify(token);
    }

    /**
     * 获取 Token 绑定的声明
     * @param token 要处理的 Token
     * @return 返回键值对集合
     */
    public static Map<String, String> getClaims(String token) {
        Map<String, String> map = new HashMap<>();
        verify(token).getClaims().forEach((k, v) -> {
            map.put(k, v.asString());
        });
        return map;
    }
}
