package com.example.studentapartmentms.service;

import com.example.studentapartmentms.pojo.MessageBoard;
import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;

import java.util.List;

/**
 * 留言板服务接口
 */
public interface MessageBoardService {

    /**
     * 获取所有留言
     */
    List<MessageBoard> allMsg();

    /**
     * 添加留言
     *
     * @param messageBoard 留言板实体类
     */
    Boolean addMsg(MessageBoard messageBoard);

    /**
     * 删除留言
     * 管理员可以删除所有人留言，学生只能删除自己的留言
     *
     * @param userId 调用者用户 ID
     * @param msgId  留言板 ID
     * @param role   调用者身份
     */
    Boolean delMsg(Integer userId, Integer msgId, RoleEnum role);

    /**
     * 分页获取指定宿舍楼留言
     * 仅管理员
     * @param dormId 宿舍楼 ID
     * @param page 当前页数
     * @param size 每页大小
     */
    Pager<MessageBoard> msgByPage(Integer dormId, Integer page, Integer size);

    /**
     * 根据用户 ID 获取宿舍楼置顶留言
     * 如果 userId 是管理员，就获取他管理的宿舍的置顶留言
     * 如果 userId 是学生，就获取他入住宿舍的置顶留言
     *
     * @param userId 用户 ID
     * @param role 用户身份
     */
    List<MessageBoard> topMsgByUserId(Integer userId, RoleEnum role);
}
