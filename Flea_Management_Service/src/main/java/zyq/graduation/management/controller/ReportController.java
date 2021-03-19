package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zyq.graduation.management.mapper.ReportMapper;
import zyq.graduation.management.pojo.Report;
import zyq.graduation.management.pojo.ReportReturn;

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

}
