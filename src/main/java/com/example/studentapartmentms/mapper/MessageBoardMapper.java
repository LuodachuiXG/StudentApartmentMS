package com.example.studentapartmentms.mapper;


import com.example.studentapartmentms.pojo.MessageBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 留言板表 Mapper 接口
 */
@Mapper
public interface MessageBoardMapper {

    /**
     * 添加留言
     *
     * @param msgBoard 留言板实体类
     */
    int addMsg(@Param("msgBoard") MessageBoard msgBoard);

    /**
     * 根据留言板 ID 删除留言板
     *
     * @param msgId 留言板 ID
     */
    int delMsgByMsgId(@Param("msgId") Integer msgId);

    /**
     * 获取所有留言
     */
    List<MessageBoard> allMsg();

    /**
     * 根据留言板 ID 获取留言
     *
     * @param msgId 留言板 ID
     */
    MessageBoard msgByMsgId(@Param("msgId") Integer msgId);

    /**
     * 根据宿舍楼 ID 获取留言
     * @param dormId 宿舍楼 ID
     */
    List<MessageBoard> msgByDormId(@Param("dormId") Integer dormId);

    /**
     * 根据宿舍楼 ID 集合获取宿舍楼的置顶留言
     * @param dormIds 宿舍楼 ID 集合
     */
    List<MessageBoard> topMsgByDormIds(@Param("dormIds") List<Integer> dormIds);
}
