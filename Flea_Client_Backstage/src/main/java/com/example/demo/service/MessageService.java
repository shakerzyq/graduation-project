package com.example.demo.service;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.CommentShow;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/17 23:42
 */
public interface MessageService {

    ArrayList<Comment> s_getGoodsComment(String flea_id,String table);


//    void s_updateMessageViewStatus(String id, String table);
}
