package com.example.studentapartmentms.service;

import com.example.studentapartmentms.pojo.MessageBoard;

/**
 * 留言板服务接口
 */
public interface MessageBoardService {

    /**
     * 获取所有留言
     * @return 留言集合
     */
    MessageBoard allMsgBoard();
}
