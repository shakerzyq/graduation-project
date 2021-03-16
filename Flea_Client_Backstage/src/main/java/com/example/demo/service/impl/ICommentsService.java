package com.example.demo.service.impl;

import com.example.demo.mapper.CommentsMapper;
import com.example.demo.mapper.ForGoodsMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.CommentShow;
import com.example.demo.pojo.User;
import com.example.demo.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 周杨清
 * @date 2021/2/26 10:28
 */
@Service
public class ICommentsService implements CommentsService {

    @Autowired
    CommentsMapper commentsMapper;

    @Autowired
    ForGoodsMapper forGoodsMapper;

    @Override
    public boolean s_sendComments(Comment comment) {
        /*comment.setPut_time(System.currentTimeMillis());
        comment.setComment_id(System.currentTimeMillis()+""+comment.getObserver_id());*/
        System.out.println("插入时间为："+comment.getPut_time()+" 评论id为： "+comment.getComment_id());
        //插入评论父子表
        /*if (comment.getCommented_id().length()!=0){
            commentsMapper.InsertCommentRelation(comment.getComment_id(),comment.getComment_f_id());
        }*/
        if (comment.getComment_rank()==0){//插入父评论表
            return commentsMapper.insertComment(comment);
        }else{//插入子评论表

            return commentsMapper.insertComment_1(comment);
        }



    }

    @Override
    public LinkedList<CommentShow> s_getComments(String goodsid, int start, int num,String fleaid) {

        LinkedList<Comment>  comments = commentsMapper.selectComments(goodsid,start,num);
        LinkedList<CommentShow> commentShows = new LinkedList<>();
        Map<String,CommentShow> map_f = new HashMap<>();
        Map<String,CommentShow> map_c = new HashMap<>();


        for (Comment comment:comments){
            CommentShow commentShow = new CommentShow();
            commentShow.setComment(comment.getComment());
            commentShow.setComment_id(comment.getComment_id());

            commentShow.setObserver_id(comment.getObserver_id());
            commentShow.setObserver_name(commentsMapper.selectUserNameInfo( comment.getObserver_id()));
            commentShow.setObserver_icon(commentsMapper.selectUserIconInfo(comment.getObserver_id()));
            commentShow.setComment_rank(comment.getComment_rank());
            commentShow.setCommented_name(comment.getCommented_username());
            commentShow.setPut_time(comment.getPut_time() + "");
            commentShow.setComment_like(commentsMapper.selectLikeNum(comment.getComment_id()));

            commentShow.setLikeJudge(commentsMapper.selectUserLike(comment.getComment_id(),fleaid));
            commentShow.setJudgeLength(0);

            ArrayList<Comment> childs =  commentsMapper.selectChildComments(comment.getComment_id());
            LinkedList<CommentShow> commentsList = new LinkedList<>();
            if (childs.size()>0){

                for (Comment comment1:childs){
                    CommentShow commentShow1 = new CommentShow();
                    commentShow1.setComment(comment1.getComment());
                    commentShow1.setComment_id(comment1.getComment_id());

                    commentShow1.setObserver_id(comment1.getObserver_id());
                    commentShow1.setObserver_name(commentsMapper.selectUserNameInfo( comment1.getObserver_id()));
                    commentShow1.setObserver_icon(commentsMapper.selectUserIconInfo(comment1.getObserver_id()));
                    commentShow1.setComment_rank(comment1.getComment_rank());
                    commentShow1.setCommented_name(comment1.getCommented_username());
                    commentShow1.setPut_time(comment1.getPut_time() + "");
                    commentShow1.setComment_like(commentsMapper.selectLikeNum(comment1.getComment_id()));

                    commentShow1.setLikeJudge(commentsMapper.selectUserLike(comment1.getComment_id(),fleaid));
                    commentShow1.setJudgeLength(0);
                    commentShow1.setComment_f_id(commentShow.getComment_id());
                    commentsList.add(commentShow1);
                }
            }
            commentShow.setChild_comment(commentsList);
            commentShows.add(commentShow);
            //System.out.println("likeJudge为： "+commentShow.getLikeJudge());
        }
        return commentShows;

        /*for (Comment comment:comments){
            CommentShow commentShow = new CommentShow();
            commentShow.setComment(comment.getComment());
            commentShow.setComment_id(comment.getComment_id());

            commentShow.setObserver_id(comment.getObserver_id());
            commentShow.setObserver_name(commentsMapper.selectUserNameInfo( comment.getObserver_id()));
            commentShow.setObserver_icon(commentsMapper.selectUserIconInfo(comment.getObserver_id()));
            commentShow.setComment_rank(comment.getComment_rank());
            commentShow.setCommented_name(comment.getCommented_username());
            commentShow.setPut_time(comment.getPut_time() + "");
            commentShow.setComment_like(commentsMapper.selectLikeNum(comment.getComment_id()));

            commentShow.setLikeJudge(commentsMapper.selectUserLike(comment.getComment_id(),fleaid));
            commentShow.setJudgeLength(0);
            System.out.println("likeJudge为： "+commentShow.getLikeJudge());
            if (comment.getComment_rank().equals("0")) {
                map_f.put(commentShow.getComment_id(), commentShow);
            }else {
                System.out.println("插入子评论");
                //插入父级评论id
                commentShow.setComment_id_f(commentsMapper.selectFatherId(commentShow.getComment_id()));
                map_c.put(commentShow.getComment_id(), commentShow);
                System.out.println("插入时：子评论id为"+commentShow.getComment_id()+"  子评论内容为："+map_c.get(commentShow.getComment_id()));
            }
        }

        for (String key:map_f.keySet()){
            System.out.println("key为"+key);
            ArrayList<String> childs = commentsMapper.selectChilds(map_f.get(key).getComment_id());
            System.out.println("其子评论为："+childs);
            System.out.println("父评论对象为："+map_f.get(key));
            LinkedList<CommentShow> list = new LinkedList<>();
            if (childs.size()>0){
                for (String childId:childs){
                    System.out.println("子评论id为"+childId);
                    System.out.println("子评论内容为"+map_c.get(childId));
                    System.out.println(map_f.get(key));
                    list.add(map_c.get(childId));
                }
            }
            System.out.println("list为："+list);
            map_f.get(key).setChild_comment(list);

        }
        return new LinkedList<>(map_f.values());*/
    }

   /* @Override
    public LinkedList<CommentShow> s_getComments(String goodsid, int start, int num,String fleaid) {

        LinkedList<Comment>  comments = commentsMapper.selectComments(goodsid,start,num);
        LinkedList<CommentShow> commentShows = new LinkedList<>();
        Map<String,CommentShow> map_f = new HashMap<>();
        Map<String,CommentShow> map_c = new HashMap<>();

        for (Comment comment:comments){
            CommentShow commentShow = new CommentShow();
            commentShow.setComment(comment.getComment());
            commentShow.setComment_id(comment.getComment_id());

            commentShow.setObserver_id(comment.getObserver_id());
            commentShow.setObserver_name(commentsMapper.selectUserNameInfo( comment.getObserver_id()));
            commentShow.setObserver_icon(commentsMapper.selectUserIconInfo(comment.getObserver_id()));
            commentShow.setComment_rank(comment.getComment_rank());
            commentShow.setCommented_name(comment.getCommented_username());
            commentShow.setPut_time(comment.getPut_time() + "");
            commentShow.setComment_like(commentsMapper.selectLikeNum(comment.getComment_id()));

            commentShow.setLikeJudge(commentsMapper.selectUserLike(comment.getComment_id(),fleaid));
            commentShow.setJudgeLength(0);
            System.out.println("likeJudge为： "+commentShow.getLikeJudge());
            if (comment.getComment_rank().equals("0")) {
                map_f.put(commentShow.getComment_id(), commentShow);
            }else {
                System.out.println("插入子评论");
                //插入父级评论id
                commentShow.setComment_id_f(commentsMapper.selectFatherId(commentShow.getComment_id()));
                map_c.put(commentShow.getComment_id(), commentShow);
                System.out.println("插入时：子评论id为"+commentShow.getComment_id()+"  子评论内容为："+map_c.get(commentShow.getComment_id()));
            }
        }

        for (String key:map_f.keySet()){
            System.out.println("key为"+key);
            ArrayList<String> childs = commentsMapper.selectChilds(map_f.get(key).getComment_id());
            System.out.println("其子评论为："+childs);
            System.out.println("父评论对象为："+map_f.get(key));
            LinkedList<CommentShow> list = new LinkedList<>();
            if (childs.size()>0){
                for (String childId:childs){
                    System.out.println("子评论id为"+childId);
                    System.out.println("子评论内容为"+map_c.get(childId));
                    System.out.println(map_f.get(key));
                    list.add(map_c.get(childId));
                }
            }
            System.out.println("list为："+list);
            map_f.get(key).setChild_comment(list);

        }
        return new LinkedList<>(map_f.values());
    }*/

    @Override
    public Boolean s_commentLikeAction(String commentedid, String fledid, Integer type,String goodsid) {

        if (type==1){
            return  commentsMapper.insertCommentLike(commentedid,fledid,goodsid);
        }else{
            return  commentsMapper.deleteCommentLike(commentedid,fledid,goodsid);
        }


    }

    @Override
    public Boolean s_commentDelete(String commentid,Integer rank) {
        if (rank==0){//删除一级评论，子评论也被删除
            commentsMapper.deleteChildComment(commentid);
            return  commentsMapper.deleteComment(commentid);
        }else{
            return  commentsMapper.deleteComment1(commentid);
        }


    }
}
