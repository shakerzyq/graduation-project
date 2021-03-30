package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zyq.graduation.management.mapper.LoginRegisterMapper;
import zyq.graduation.management.pojo.AdminGeneral;
import zyq.graduation.management.pojo.ReturnClass;
import zyq.graduation.management.service.LoginRegisterService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author 周杨清
 * @date 2021/3/28 18:15
 */
@RestController
@RequestMapping("/loginRegister")
public class LoginRegisterController {

    @Autowired
    LoginRegisterService loginRegisterService;

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
     *选择校区
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/selectSchoolArea/{college}")
    public ArrayList<String> selectSchoolArea(@PathVariable("college") String college){
        System.out.println("学校为："+college);
        return loginRegisterMapper.selectAreaByCollege(college);
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/upload/{type}")
    public ReturnClass<String> uploadFile(@RequestParam("file") MultipartFile file
            ,@PathVariable("type") String type){
        System.out.println("商品图片上传,上传类型为："+type);
        String result = loginRegisterService.upload(file,type);
        System.out.println(result);

        return new ReturnClass<String>(0,"success",1,result);
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/submitAdminInfo")
    public ReturnClass<String> submitAdminInfo(@RequestBody AdminGeneral adminGeneral){
        System.out.println("hello");
        adminGeneral.setUuid(UUID.randomUUID().toString());
        System.out.println(adminGeneral);
        String judge=loginRegisterMapper.verifyAdminInfo(adminGeneral)==0?"0":"1";
        judge+=loginRegisterMapper.verifyAdminAccount(adminGeneral)==0?"0":"1";
        //先验证该信息是否已经被注册
        switch (judge){
            case "11":
                return new ReturnClass<String>(1,"该学号和邮箱都已被注册");
            case "10":
                return new ReturnClass<String>(1,"该学号已被注册");
            case "01":
                new ReturnClass<String>(1,"该邮箱都已被注册");
            case "00":
                return new ReturnClass<String>(0,loginRegisterMapper.insertGeneralAdmin(adminGeneral)?"success":"error") ;
            default:
                return new ReturnClass<String>(1,"error");
        }
    }

}

