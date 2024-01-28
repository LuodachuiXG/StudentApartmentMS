package com.example.studentapartmentms.common;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.UserService;
import com.example.studentapartmentms.service.impl.UserServiceImpl;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
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
            UserService userService,
            HttpServletRequest request,
            RoleEnum role
    ) {
        // 根据 Token 获取用户
        String token = request.getHeader("token");
        isRole(userService, token, role);
    }

    /**
     * 根据 Token 判断绑定的用户是否是指定的角色
     * 如果用户角色不符，抛出 JWT 验证异常
     *
     * @param token Token
     * @param role  用户角色
     */
    public static void isRole(
            UserService userService,
            String token,
            RoleEnum role
    ) {
        // 根据 Token 获取用户
        User user = userService.userByToken(token);
        // 检查当前用户是否指定角色
        if (user.getRole() != role) {
            throw new JWTVerificationException("无权访问受保护资源");
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
}
