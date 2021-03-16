package com.example.demo.controller;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.CommentShow;
import com.example.demo.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

/**
 * @author 周杨清
 * @date 2021/2/26 10:14
 */
@RestController
@RequestMapping("/comment")
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    /**
     * 评论
     * @param comment
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/sendcomment")
    public Boolean sendComments(@RequestBody Comment comment){
        System.out.println("收到的comment："+comment);
        return commentsService.s_sendComments(comment);
    }


    /**
     * 获取评论信息
     * @param goodsid
     * @param pagenum
     * @param fleaid
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getcomment/{goodsid}/{pagenum}/{fleaid}")
    public LinkedList<CommentShow> getComments(@PathVariable("goodsid") String goodsid,@PathVariable("pagenum") String pagenum,@PathVariable("fleaid") String fleaid){
        System.out.println("goodsid:"+goodsid+"  pagenum:"+pagenum);
        LinkedList<CommentShow> linkedList =  commentsService.s_getComments(goodsid,(Integer.parseInt(pagenum)-1)*4,4,fleaid);
        System.out.println("查询结果为"+linkedList);
        return  linkedList;
    }

    /**
     * 评论点赞
     * @param commentedid
     * @param fledid
     * @param type
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/commentlike/{commentedid}/{fledid}/{type}/{goodsid}")
    public Boolean commentLike(@PathVariable("commentedid") String commentedid,
                               @PathVariable("fledid") String fledid,
                               @PathVariable("type") Integer type,
                               @PathVariable("goodsid") String goodsid){
        System.out.println(commentedid+fledid+type);
        return commentsService.s_commentLikeAction(commentedid,fledid,type,goodsid);
    }

    /**
     * 删除评论
     * @param commentid
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/commentdelete/{commentid}/{rank}")
    public Boolean commentDelete(@PathVariable("commentid") String commentid,@PathVariable("rank") String rank){
        System.out.println("评论id为："+commentid+"rank为："+rank);

        return  commentsService.s_commentDelete(commentid,Integer.parseInt(rank));
    }

}
