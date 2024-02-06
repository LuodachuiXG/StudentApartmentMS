package com.example.studentapartmentms.controller;

import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.expcetion.MyException;
import com.example.studentapartmentms.pojo.MessageBoard;
import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.service.MessageBoardService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/msg")
@ResponseBody
@Slf4j
public class MessageBoardController {

    @Resource
    MessageBoardService messageBoardService;

    /**
     * 添加留言
     *
     * @param messageBoard 留言板实体类
     */
    @PostMapping
    public Boolean addMsg(
            HttpServletRequest request,
            @RequestBody MessageBoard messageBoard
    ) {
        // 检查参数是否匹配
        if (messageBoard.getDormitoryId() == null || messageBoard.getContent().isBlank()) {
            Utils.throwMismatchParamException();
        }
        // 获取当前调用用户 ID
        messageBoard.setUserId(Utils.getUserIdByRequest(request));
        // 非管理员不能设置置顶留言
        if (messageBoard.getTop() != null && messageBoard.getTop()) {
            try {
                Utils.isRole(request, RoleEnum.ADMIN);
            } catch (MyException e) {
                throw new MyException("非管理员不能设置留言置顶");
            }
        }
        log.info("ADD_MSG: " + messageBoard);
        return messageBoardService.addMsg(messageBoard);
    }

    /**
     * 删除留言
     * 管理员可以删除所有人留言，学生只能删除自己的留言
     *
     * @param msgId 留言板 ID
     */
    @DeleteMapping("/{msgId}")
    public Boolean deleteMsg(
            HttpServletRequest request,
            @PathVariable("msgId") Integer msgId
    ) {
        // 获取调用者用户 ID
        Integer userId = Utils.getUserIdByRequest(request);
        // 判断当前用户是否是管理员
        RoleEnum role = Utils.getRoleByRequest(request);
        log.info("DEL_MSG: " + msgId);
        return messageBoardService.delMsg(userId, msgId, role);
    }

    /**
     * 分页获取指定宿舍楼留言
     *
     * @param dormId 宿舍楼 ID
     * @param page   当前页数
     * @param size   每页大小
     */
    @GetMapping("/{dormId}/{page}/{size}")
    public Pager<MessageBoard> msgByPage(
            HttpServletRequest request,
            @PathVariable("dormId") Integer dormId,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        // 返回留言
        return messageBoardService.msgByPage(dormId, page, size);
    }

    /**
     * 根据用户 ID 获取宿舍楼置顶留言
     * 如果 userId 是管理员，就获取他管理的宿舍的置顶留言
     * 如果 userId 是学生，就获取他入住宿舍的置顶留言
     *
     * @param userId 用户 ID
     */
    @GetMapping("/top/{userId}")
    public List<MessageBoard> topMsgByUserId(
            HttpServletRequest request,
            @PathVariable("userId") Integer userId
    ) {
        // 获取当前用户身份
        RoleEnum role = Utils.getRoleByRequest(request);
        // 返回置顶留言
        return messageBoardService.topMsgByUserId(userId, role);
    }
}


