package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.ReportMapper;
import zyq.graduation.management.mapper.UserMapper;
import zyq.graduation.management.pojo.Report;
import zyq.graduation.management.pojo.ReportReturn;
import zyq.graduation.management.service.ReportService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/20 3:25
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    ReportService reportService;

    @Autowired
    UserMapper userMapper;

    /**
     * 获取所有未处理的举报单
     * @param page
     * @param limit
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getPendingReports")
    public ReportReturn getPendingReports(@PathParam("page") String page
            ,@PathParam("limit") String limit){
        ArrayList<Report> reports = reportMapper.selectReport();
        return new ReportReturn(0,"",reports.size(),reports);
    }

    /**
     * 处理举报单
     * @param reportId
     * @param type
     * @param content
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/disposeReport/{reportId}/{content}/{type}")
    public Boolean  disposeReport(
            @PathVariable("reportId") String reportId
            ,@PathVariable("type") String type
            ,@PathVariable("content") String content){
        return reportService.disposeReport(type,reportId,content);

    }

    /**
     * 处理用户的状态
     * @param complianed_userid
     * @param type
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/banOrUnbanUser/{type}/{complianed_userid}")
    public Boolean banOrUnbanUser(
            @PathVariable("complianed_userid") String complianed_userid
            ,@PathVariable("type") String type){
        System.out.println("id为："+complianed_userid+"type为："+type);
        if (type.equals("true")){
            return userMapper.BanUser(complianed_userid);
        }else{
            return userMapper.UnBanUser(complianed_userid);
        }
    }
}
