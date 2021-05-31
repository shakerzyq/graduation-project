package zyq.graduation.management.service;

import zyq.graduation.management.pojo.Report;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/20 20:00
 */
public interface AdminReportService {
    Boolean disposeReport(String type, String reportId, String content,String complain_userid,String complained_userid,String goods_id,String admin_id);


    ArrayList<Report> getPendingReports(Integer page,Integer limit, Integer type, String flea_id, String goodsId);

    ArrayList<Report> getAccomplishReports(Integer page,Integer limit,Integer reportType, Integer resultType,String adminAccount);
}
