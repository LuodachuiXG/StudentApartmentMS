package com.example.studentapartmentms.config;

import com.example.studentapartmentms.pojo.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@ControllerAdvice
public class GlobalResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
            MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType
    ) {
        // 返回 true 表示对所有带有 @ResponseBody 的方法应用此转换器
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body, MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response
    ) {
        if (body instanceof Response<?>) {
            // 如果响应体已经是统一格式，则无需再次包装
            return body;
        } else {
            Response<Object> apiResponse = new Response<>();
            // 根据业务逻辑设置状态码和消息
            apiResponse.setCode(HttpStatus.OK.value());
            // 将原始响应内容放入 data 字段
            apiResponse.setData(body);
            return apiResponse;
        }
    }
}