package com.example.studentapartmentms.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.studentapartmentms.common.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 拦截器
 */
@Slf4j
public class JWTInterceptors implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        // 获取请求地址
        String requestUrl = request.getRequestURI();
        // 获取请求方法
        String requestMethod = request.getMethod();

        if (requestMethod.equals("POST") && requestUrl.equals("/user")) {
            // 注册用户接口，放行
            return true;
        }

        // 获取请求头中携带的 Token
        String token = request.getHeader("token");
        String errMsg;
        try {
            JWTUtils.verify(token);
            // Token 没问题，放行
            return true;
        } catch (SignatureVerificationException e) {
            log.info(e.getMessage());
            errMsg = "Token 签名无效";
        } catch (TokenExpiredException e) {
            log.info(e.getMessage());
            errMsg = "Token 过期";
        } catch (AlgorithmMismatchException e) {
            log.info(e.getMessage());
            errMsg = "Token 算法不一致";
        } catch (Exception e) {
            log.info(e.getMessage());
            errMsg = "无权访问受保护资源";
        }
        // 抛出 JWT 验证异常，携带异常信息
        throw new JWTVerificationException(errMsg);
    }
}
