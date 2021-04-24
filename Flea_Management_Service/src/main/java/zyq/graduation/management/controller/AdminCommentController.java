package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.AdminCommentMapper;
import zyq.graduation.management.pojo.CommentReport;
import zyq.graduation.management.pojo.ReturnClass;
import zyq.graduation.management.service.AdminCommentService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/27 15:55
 */
@RestController
@RequestMapping("/comment")
public class AdminCommentController {

    @Autowired
    AdminCommentMapper adminCommentMapper;

    @Autowired
    AdminCommentService adminCommentService;

    /**
     * 查询所有未处理的评论举报单
     * @param page
     * @param limit
     * @param userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getCommentReports")
    public ReturnClass getCommentReports(@PathParam("page") Integer page
            , @PathParam("limit") Integer limit
            , @PathParam("userId") String userId
            , @PathParam("type") String type){
        System.out.println("page:"+page+"limit:"+limit+" type:"+type);
        ArrayList<CommentReport> commentReport = adminCommentService.getCommentReport(page-1,limit,userId,type);
        System.out.println(commentReport);
        return new ReturnClass<>(0,"",commentReport.size(),commentReport);
    }

    /**
     * 对举报内容进行审核
     * @param complainId 举报单id
     * @param status 审核结果，0：拒绝，1：通过
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/disposeReport/{complainId}/{status}")
    public Boolean disposeReport(@PathVariable("complainId") String complainId,@PathVariable("status") Integer status){
        System.out.println("complainId:"+complainId+"status:"+status);
        return adminCommentMapper.updataReportStatus(complainId,status);
    }

    /**
     * 查询已经审核完成的举报单
     * @param userId 查询条件 用户id
     * @param status 查询状态，0：拒绝，1；通过
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getAccomplishReports")
    public ReturnClass getAccomplishReports(
            @PathParam("page") Integer page
            ,@PathParam("limit") Integer limit
            ,@PathParam("userId") String userId
            , @PathParam("status") String status){
        System.out.println("userId:"+userId+"status:"+status);
        ArrayList<CommentReport> commentReport = adminCommentService.getAccomplishReports(page-1,limit,userId,status);
        return new ReturnClass<>(0,"",commentReport.size(),commentReport);
    }

}
