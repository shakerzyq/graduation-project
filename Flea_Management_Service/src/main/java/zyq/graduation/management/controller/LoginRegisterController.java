package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.LoginRegisterMapper;
import zyq.graduation.management.pojo.AdminGeneral;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/28 18:15
 */
@RestController
@RequestMapping("/loginRegister")
public class LoginRegisterController {

    @Autowired
    LoginRegisterMapper loginRegisterMapper;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/adminLogin")
    public Boolean generalAdminLogin(@RequestBody AdminGeneral admin){
        System.out.println("user为："+admin);
        Integer res = loginRegisterMapper.selectGeneralAdmin(admin);
        return res != 0;
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getCollege/{province}")
    public ArrayList<String> getCollege(@PathVariable String province){
        return loginRegisterMapper.selectCollegeByProvince(province);
    }

    /**
     *
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/selectSchoolArea/{college}")
    public ArrayList<String> selectSchoolArea(@PathVariable("college") String college){
        System.out.println("学校为："+college);
        return loginRegisterMapper.selectAreaByCollege(college);
    }
}

