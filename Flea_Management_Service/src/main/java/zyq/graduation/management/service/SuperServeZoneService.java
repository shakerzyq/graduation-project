package zyq.graduation.management.service;

import zyq.graduation.management.pojo.AdminGeneral;
import zyq.graduation.management.pojo.Services;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/5/3 16:50
 */
public interface SuperServeZoneService {
    ArrayList<Services> getServices(Integer page, Integer limit);

    ArrayList<Services> selectCheckedInfoByCondition(Integer page, Integer limit, String province, String college, String area, String collegename);
}
