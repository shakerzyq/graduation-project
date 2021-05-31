package zyq.graduation.management.service;

import zyq.graduation.management.pojo.AdminGeneral;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/4/27 21:08
 */
public interface SuperAdminService {
    ArrayList<AdminGeneral> selectUnCheckedInfoByCondition(Integer page, Integer limit, String province, String college, String area, String name);

    ArrayList<AdminGeneral> selectCheckedInfoByCondition(Integer page, Integer limit, String province, String college, String area, String name);
}
