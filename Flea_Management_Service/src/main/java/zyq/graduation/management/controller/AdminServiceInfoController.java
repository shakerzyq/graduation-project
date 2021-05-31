package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.AdminServiceInfoMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author 周杨清
 * @date 2021/4/27 15:18
 */
@RestController
@RequestMapping("/serviceinfo")
public class AdminServiceInfoController {

    @Autowired
    AdminServiceInfoMapper adminServiceInfoMapper;

    /**
     * 获取开放服务的省份
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getProvince")
    public HashSet<String> getProvince(){
        return  adminServiceInfoMapper.selectProvince();
    }

    /**
     * 获取对应省份开放的学校
     * @param province
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)

    @GetMapping("/getCollege/{province}")
    public HashSet<String> getCollege(@PathVariable("province") String province){
        System.out.println("省份为"+province);
        return adminServiceInfoMapper.selectCollegeByProvince(province);
    }

    /**
     * 获取院校对应开放的校区
     * @param college
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getCollegeArea/{college}")
    public List<String> getCollegeArea(@PathVariable("college") String college){
        System.out.println("college为："+college);
        return adminServiceInfoMapper.selectAreaByCollege(college);
    }

}
