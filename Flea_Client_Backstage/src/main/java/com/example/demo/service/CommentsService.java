package com.example.demo.service;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.CommentShow;
import com.example.demo.pojo.putpojo.CommentReport;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * @author 周杨清
 * @date 2021/2/26 10:25
 */
@Component
public interface CommentsService  {

    public boolean s_sendComments(Comment comment);

    LinkedList<CommentShow> s_getComments(String goodsid, int start, int num,String fleaid);

    Boolean s_commentLikeAction(String commentedid, String fledid, Integer type,String goodsid);

    Boolean s_commentDelete(String commentid, Integer rank);

    Boolean reportComment(CommentReport commentReport);
}
