package zyq.graduation.management.service;

import zyq.graduation.management.pojo.GoodsReport;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/5/6 16:28
 */
public interface AdminGoodsService {
    ArrayList<GoodsReport> getGoodsReports(Integer page, Integer limit, String adminAccount);

    ArrayList<GoodsReport> getGoodsAccomplishReports(int page, Integer limit,String userId,String status,String adminAccount);

    Boolean disposeGoodsReport(String type, String reportId, String content, String complain_userid, String complained_userid, String goods_id, String adminAccount);
}
