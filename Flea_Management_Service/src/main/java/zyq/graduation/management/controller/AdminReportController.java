package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.AdminUserMapper;
import zyq.graduation.management.pojo.Report;
import zyq.graduation.management.pojo.ReportReturn;
import zyq.graduation.management.service.AdminReportService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/20 3:25
 */
@RestController
@RequestMapping("/report")
public class AdminReportController {

    @Autowired
    AdminReportService adminReportService;

    @Autowired
    AdminUserMapper adminUserMapper;



    /**
     * 获取所有未处理的特殊举报单
     * @param page
     * @param limit
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getPendingReports")
    public ReportReturn getPendingReports(@PathParam("page") Integer page
            ,@PathParam("limit") Integer limit
            ,@PathParam("type") String type
            ,@PathParam("flea_id") String flea_id
            ,@PathParam("goodsId") String goodsId
            ){
        System.out.println(page);
        System.out.println(limit);
        System.out.println("type:"+type);
        System.out.println("flea_id:"+flea_id);
        System.out.println("goodsId:"+goodsId);
        ArrayList<Report> reports = adminReportService.getPendingReports(page-1,limit,Integer.parseInt(type),flea_id,goodsId);
        System.out.println("查询到的结果"+reports);
        return new ReportReturn(0,"",reports.size(),reports);
    }

    /**
     * 获取已经处理完成的特殊举报单
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getAccomplishReports")
    public ReportReturn getAccomplishReports(
            @PathParam("page") Integer page
            ,@PathParam("limit") Integer limit
            ,@PathParam("adminAccount") String adminAccount
            ,@PathParam("reportType") Integer reportType
            ,@PathParam("resultType") Integer resultType){
        System.out.println("limit: "+limit+"page"+page);
        System.out.println("reportType: "+reportType+"resultType: "+resultType);
        ArrayList<Report> reports = adminReportService.getAccomplishReports(page-1,limit,reportType,resultType,adminAccount);
        System.out.println("结果为："+reports);
        return new ReportReturn(0,"",reports.size(),reports);
    }

    /**
     * 处理特殊举报单
     * @param reportId
     * @param type
     * @param content
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/disposeReport/{reportId}/{content}/{type}/{complain_userid}/{complained_userid}/{goods_id}/{admin_id}")
    public Boolean  disposeOrderReport(
            @PathVariable("reportId") String reportId
            ,@PathVariable("type") String type
            ,@PathVariable("content") String content
            ,@PathVariable("complain_userid") String complain_userid
            ,@PathVariable("complained_userid") String complained_userid
            ,@PathVariable("goods_id") String goods_id
            ,@PathVariable("admin_id") String admin_id
    ){
        System.out.println("++++++++++++++++type:"+type+"reportId:"+reportId+"content:"+content+" "+complain_userid+" "+complained_userid+" "+goods_id+" "+admin_id);
        return adminReportService.disposeReport(type,reportId,content,complain_userid,complained_userid,goods_id,admin_id);
    }

    /**
     *
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
            return adminUserMapper.updateBanstatus(1,complianed_userid);
        }else{
            return adminUserMapper.updateBanstatus(1,complianed_userid);
        }
    }


}
