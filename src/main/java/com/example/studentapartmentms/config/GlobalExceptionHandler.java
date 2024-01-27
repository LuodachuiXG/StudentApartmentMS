package com.example.studentapartmentms.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.studentapartmentms.expcetion.MyException;
import com.example.studentapartmentms.pojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理 JWT 验证异常 [MyException]
     * @param e 异常类
     * @return 异常信息
     */
    @ExceptionHandler(JWTVerificationException.class)
    @ResponseBody
    public ResponseEntity<Response<Object>> handleJWTVerificationException(Exception e) {
        Response<Object> apiResponse = new Response<>();
        apiResponse.setCode(HttpStatus.UNAUTHORIZED.value());
        apiResponse.setErrMsg(e.getMessage());
        apiResponse.setData(null);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    /**
     * 处理自定义异常 [MyException]
     * @param e 异常类
     * @return 异常信息
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public ResponseEntity<Response<Object>> handleMyException(Exception e) {
        Response<Object> apiResponse = new Response<>();
        apiResponse.setCode(HttpStatus.CONFLICT.value());
        apiResponse.setErrMsg(e.getMessage());
        apiResponse.setData(null);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    /**
     * 处理未找到资源异常 [NotFoundException]，[NoResourceFoundException]
     * @param e 异常类
     * @return 异常信息
     */
    @ExceptionHandler({
            NotFoundException.class,
            NoResourceFoundException.class,
            HttpRequestMethodNotSupportedException.class
    })
    public String handleNotFoundException(Exception e) {
        return "/error/404.html";
    }

    /**
     * 处理其他异常 [Exception]
     * @param e 异常类
     * @return 异常信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Response<Object>> handleException(Exception e) {
        Response<Object> apiResponse = new Response<>();
        apiResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiResponse.setErrMsg("服务器内部错误");
        apiResponse.setData(null);
        e.printStackTrace();
        return ResponseEntity.status(500).body(apiResponse);
    }
}
