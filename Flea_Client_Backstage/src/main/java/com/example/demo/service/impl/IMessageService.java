package com.example.demo.service.impl;

import com.example.demo.mapper.MessageMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.CommentShow;
import com.example.demo.service.MessageService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/17 23:43
 */
@Service
public class IMessageService implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public ArrayList<Comment> s_getGoodsComment(String flea_id,String table) {
        if(table.equals("comment")){
            return messageMapper.selectCommentA(flea_id);
        }else {
            return messageMapper.selectCommentB(flea_id);
        }

    }

//    @Override
//    public void s_updateMessageViewStatus(String id, String table) {
//        if (table.equals("comment")){
//            messageMapper.updateViewStatus(id);
//        }else {}
//    }
}
