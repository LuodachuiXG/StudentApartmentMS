package com.example.studentapartmentms.common;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.studentapartmentms.expcetion.MyException;
import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.UserService;
import com.example.studentapartmentms.service.impl.UserServiceImpl;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;

import javax.management.relation.Role;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
@Slf4j
public class Utils {
    /**
     * 判断文本是否是数字
     *
     * @param str 要判断的文本
     * @param length 数字长度，小于等于 0 表示任意长度
     * @return 数字返回 true，反之 false
     */
    public static boolean isNumber(String str, Integer length) {
        Pattern pattern;
        String regex = "\\d+";
        if (length > 0) {
            regex = "\\d{" + length + "}";
        }
        pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断文本是否是数字
     *
     * @param str 要判断的文本
     * @return 数字返回 true，反之 false
     */
    public static boolean isNumber(String str) {
        return isNumber(str, -1);
    }



    /**
     * 根据 Token 判断绑定的用户是否是指定的角色
     * 如果用户角色不符，抛出 JWT 验证异常
     *
     * @param request Request 请求，用于获取 Token
     * @param role    用户角色
     */
    public static void isRole(
            HttpServletRequest request,
            RoleEnum role
    ) {
        // 根据 Token 获取用户
        String token = request.getHeader("token");
        isRole(token, role);
    }

    /**
     * 根据 Token 判断绑定的用户是否是指定的角色
     * 如果用户角色不符，抛出 JWT 验证异常
     *
     * @param token Token
     * @param role  用户角色
     */
    public static void isRole(
            String token,
            RoleEnum role
    ) {
        // 从 Token 获取用户对应身份
        Map<String, String> claims = JWTUtils.getClaims(token);
        String roleStr = claims.get("role");

        // 检查当前用户是否指定角色
        if (!roleStr.equals(role.toString())) {
            throw new MyException("无权访问受保护资源");
        }
    }

    /**
     * 根据 Token 获取用户 ID
     *
     * @param token Token
     */
    public static Integer getUserIdByToken(String token) {
        // 获取当前 Token Claims
        Map<String, String> claims = JWTUtils.getClaims(token);
        // 获取当前 Token 绑定的用户 ID
        String userId = claims.get("userId");

        if (!isNumber(userId)) {
            // userId 不是数字
            throw new JWTVerificationException("Token 异常");
        }
        return Integer.valueOf(userId);
    }

    /**
     * 根据 HttpServletRequest 获取调用者用户 ID
     * @param request HttpRequest 请求
     * @return 用户 ID
     */
    public static Integer getUserIdByRequest(HttpServletRequest request) {
        String token = request.getHeader("token");
        return getUserIdByToken(token);
    }

    /**
     * 根据 HttpServletRequest 获取调用者用户身份
     * @param request HttpRequest 请求
     * @return 用户身份
     */
    public static RoleEnum getRoleByRequest(HttpServletRequest request) {
        try {
            isRole(request, RoleEnum.ADMIN);
            return RoleEnum.ADMIN;
        } catch (MyException e) {
            return RoleEnum.STUDENT;
        }
    }

    /**
     * 用于分页的操作
     * 在执行过  PageHelper.startPage() 方法后获取 Pager 分页实体类
     * @param list SQL 返回的结果集
     * @param page 当前页数
     * @param size 每页大小
     * @return 封装好的 Pager 对象
     */
    public static <T> Pager<T> getPager(List<? extends T> list, Integer page, Integer size) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        // 获取项目总数
        long totalData = pageInfo.getTotal();
        // 获取总页数
        int totalPage = pageInfo.getPages();
        Pager<T> pager = new Pager<>();
        pager.setPage(page);
        pager.setSize(size);
        pager.setData(pageInfo.getList());
        pager.setTotalData(totalData);
        pager.setTotalPage(totalPage);
        return pager;
    }

    /**
     * 抛出参数不匹配异常
     */
    public static void throwMismatchParamException() {
        // 该异常类形参填什么无关紧要
        // 异常处理器只拦截对应的异常并告知调用方参数不匹配
        throw new TypeMismatchException("", String.class);
    }
}
