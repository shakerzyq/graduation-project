package zyq.graduation.management.service;

import zyq.graduation.management.pojo.CommentReport;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/27 15:58
 */
public interface CommentService {
    ArrayList<CommentReport> getCommentReport(Integer page, Integer limit, String userId,String type);

    ArrayList<CommentReport> getAccomplishReports(Integer page,Integer limit,String userId,String status);
}
