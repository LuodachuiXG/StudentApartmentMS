package com.example.studentapartmentms.controller;

import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.pojo.Dormitory;
import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.DormitoryService;
import com.example.studentapartmentms.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/dorm")
@ResponseBody
@Slf4j
public class DormitoryController {

    /** 用户服务接口 **/
    @Resource
    private UserService userService;

    /** 宿舍服务接口 **/
    @Resource
    private DormitoryService dormitoryService;

    /**
     * 获取所有宿舍
     * 仅管理员
     */
    @GetMapping
    public List<Dormitory> allDorm(HttpServletRequest request) {
        // 检查当前用户是否是管理员
        Utils.isRole(userService, request, RoleEnum.ADMIN);
        // 返回所有宿舍
        return dormitoryService.dorms();
    }

    /**
     * 分页获取宿舍
     * 仅管理员
     *
     * @param page 当前页数
     * @param size 每页大小
     */
    @GetMapping("/{page}/{size}")
    public Pager<Dormitory> dormByPage(
            HttpServletRequest request,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(userService, request, RoleEnum.ADMIN);
        // 返回所有用户信息
        return dormitoryService.dormByPage(page, size);
    }
}
