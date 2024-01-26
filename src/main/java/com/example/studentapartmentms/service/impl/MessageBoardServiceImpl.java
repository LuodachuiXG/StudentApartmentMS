package com.example.studentapartmentms.service.impl;

import com.example.studentapartmentms.mapper.MessageBoardMapper;
import com.example.studentapartmentms.pojo.MessageBoard;
import com.example.studentapartmentms.service.MessageBoardService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MessageBoardServiceImpl implements MessageBoardService {

    @Resource
    private MessageBoardMapper mapper;

    /**
     * 获取所有留言
     * @return 留言集合
     */
    @Override
    public MessageBoard allMsgBoard() {
        return null;
    }
}
