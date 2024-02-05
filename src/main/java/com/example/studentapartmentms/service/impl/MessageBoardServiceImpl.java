package com.example.studentapartmentms.service.impl;

import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.expcetion.MyException;
import com.example.studentapartmentms.mapper.MessageBoardMapper;
import com.example.studentapartmentms.pojo.*;
import com.example.studentapartmentms.service.DormitoryService;
import com.example.studentapartmentms.service.MessageBoardService;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 留言板服务接口实现类
 */
@Service
public class MessageBoardServiceImpl implements MessageBoardService {

    @Resource
    private MessageBoardMapper messageBoardMapper;

    @Resource
    private DormitoryService dormitoryService;

    /**
     * 获取所有留言
     */
    @Override
    public List<MessageBoard> allMsg() {
        return messageBoardMapper.allMsg();
    }

    /**
     * 添加留言
     *
     * @param messageBoard 留言板实体类
     */
    @Override
    public Boolean addMsg(MessageBoard messageBoard) {
        // 设置是否置顶
        if (messageBoard.getTop() == null) {
            messageBoard.setTop(false);
        }
        messageBoard.setCreateDate(LocalDateTime.now());
        return messageBoardMapper.addMsg(messageBoard) > 0;
    }

    /**
     * 删除留言
     * 管理员可以删除所有人留言，学生只能删除自己的留言
     *
     * @param userId 调用者用户 ID
     * @param msgId  留言板 ID
     * @param role   调用者身份
     */
    @Override
    public Boolean delMsg(Integer userId, Integer msgId, RoleEnum role) {
        // 首先获取留言实体类
        MessageBoard messageBoard = messageBoardMapper.msgByMsgId(msgId);
        // 判断是否是学生删除其他人的留言
        if (role == RoleEnum.STUDENT && !userId.equals(messageBoard.getUserId())) {
            throw new MyException("学生无法删除其他人的留言");
        }
        // 删除留言
        return messageBoardMapper.delMsgByMsgId(msgId) > 0;
    }

    /**
     * 分页获取留言
     *
     * @param page 当前页
     * @param size 每页条数
     */
    @Override
    public Pager<MessageBoard> msgByPage(Integer page, Integer size) {
        // 开始分页查询，执行此代码之后的 SQL 会被自动加上分页的代码
        PageHelper.startPage(page, size);
        // 此处的获取所有留言的 SQL 已经被加上了分页代码
        List<MessageBoard> list = allMsg();
        return Utils.getPager(list, page, size);
    }

    /**
     * 根据用户 ID 获取宿舍楼置顶留言
     * 如果 userId 是管理员，就获取他管理的宿舍的置顶留言
     * 如果 userId 是学生，就获取他入住宿舍的置顶留言
     *
     * @param userId 用户 ID
     * @param role   用户身份
     */
    @Override
    public List<MessageBoard> topMsgByUserId(Integer userId, RoleEnum role) {
        if (role == RoleEnum.ADMIN) {
            // 当前用户是管理员，获取他所管理的宿舍的置顶留言
            // 先获取当前管理员管理的所有宿舍
            List<Dormitory> dorms = dormitoryService.dormsByAdmin(userId);
            List<Integer> dormIds = new ArrayList<>();
            // 获取管理的宿舍的宿舍 ID
            dorms.forEach((dorm) -> dormIds.add(dorm.getDormitoryId()));
            // 根据宿舍 ID 返回置顶留言
            return messageBoardMapper.topMsgByDormIds(dormIds);
        } else {
            // 当前用户是学生，获取他所入住的宿舍的置顶留言
            // 首先获取学生所住的宿舍楼 ID
            Integer dormId = dormitoryService.studentDormIdByUserId(userId);
            // 根据宿舍 ID 返回置顶留言
            return messageBoardMapper.topMsgByDormIds(List.of(dormId));
        }
    }
}
