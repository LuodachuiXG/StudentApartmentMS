package com.example.studentapartmentms.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptors())
                // 所有请求都要使用 JWT 拦截器进行处理
                .addPathPatterns("/user/**")
                // 除了以下的接口
                .excludePathPatterns("/user/login");
    }
}
