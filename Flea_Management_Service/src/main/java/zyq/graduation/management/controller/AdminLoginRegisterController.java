package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zyq.graduation.management.mapper.AdminLoginRegisterMapper;
import zyq.graduation.management.pojo.*;
import zyq.graduation.management.service.AdminLoginRegisterService;
import zyq.graduation.management.util.SendEmailMessage;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author 周杨清
 * @date 2021/3/28 18:15
 */
@RestController
@RequestMapping("/loginRegister")
public class AdminLoginRegisterController {

    @Autowired
    AdminLoginRegisterService adminLoginRegisterService;

    @Autowired
    AdminLoginRegisterMapper adminLoginRegisterMapper;

    @Autowired
    SendEmailMessage sendEmailMessage;

    /**
     * 普通管理员登录
     * @param admin
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/adminLogin")
    public ReturnClass<String> generalAdminLogin(@RequestBody AdminGeneral admin){
        System.out.println("user为："+admin);
        AdminGeneral res = adminLoginRegisterMapper.selectGeneralAdmin(admin);
        String msg="";
        int code=0;
        if (res!=null){
            switch (res.getStatus()){
                case "0":
                    msg="success";
                    code=1;
                    break;
                case "1":
                    msg="永久封禁中";
                    code=0;
                    break;
                case "2":
                    msg="暂时封禁中";
                    code=0;
                    break;
                case "3":
                    msg="未通过审核";
                    code=0;
                    break;
                default:
                    msg="账号或密码错误";
                    code=0;
                    break;
            }
        }else{
            msg="账号或密码错误";
            code=0;
        }

        System.out.println("输出的数字为："+res);

        return new ReturnClass<String>(code,msg,1,"");
    }

    /**
     * 超级管理员登录
     * @param admin
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/superLogin")
    public ReturnClass<String> generalSuperLogin(@RequestBody AdminSuper admin){
        System.out.println("你好"+admin);
        Integer res = adminLoginRegisterMapper.selectSuperAdmin(admin);
        System.out.println("查询结果为："+res);
        String msg="账号或密码错误";
        int code=0;
        if (res==1){
            code=1;
            msg="success";
        }
        return new ReturnClass<String>(code,msg,1,"");
    }

    /**
     * 通过省份获取学校
     * @param province
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getCollege/{province}")
    public ArrayList<String> getCollege(@PathVariable String province){
        return adminLoginRegisterMapper.selectCollegeByProvince(province);
    }

    /**
     *选择校区
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/selectSchoolArea/{college}")
    public List<String> selectSchoolArea(@PathVariable("college") String college){
        System.out.println("学校为："+college);
        return Arrays.asList(adminLoginRegisterMapper.selectAreaByCollege(college).split("-"));
    }

    /**
     * 上传图片
     * @param file
     * @param type
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/upload/{type}")
    public ReturnClass<String> uploadFile(@RequestParam("file") MultipartFile file
            ,@PathVariable("type") String type){
        System.out.println("商品图片上传,上传类型为："+type);
        String result = adminLoginRegisterService.upload(file,type);
        System.out.println(result);

        return new ReturnClass<String>(0,"success",1,result);
    }

    /**
     * 注册管理员
     * @param adminGeneral
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/submitAdminInfo")
    public ReturnClass<String> RegisterAdmin(@RequestBody AdminGeneral adminGeneral){
        System.out.println("hello");
        adminGeneral.setUuid(UUID.randomUUID().toString());
        System.out.println(adminGeneral);
        String judge= adminLoginRegisterMapper.verifyAdminInfo(adminGeneral)==0?"0":"1";
        judge+= adminLoginRegisterMapper.verifyAdminAccount(adminGeneral)==0?"0":"1";
        //先验证该信息是否已经被注册
        switch (judge){
            case "11":
                return new ReturnClass<String>(1,"该学号和邮箱都已被注册");
            case "10":
                return new ReturnClass<String>(1,"该学号已被注册");
            case "01":
                new ReturnClass<String>(1,"该邮箱都已被注册");
            case "00":
                return new ReturnClass<String>(0, adminLoginRegisterMapper.insertGeneralAdmin(adminGeneral)?"success":"error") ;
            default:
                return new ReturnClass<String>(1,"error");
        }
    }

    /**
     * 查询等待审核的管理员申请信息
     * @param page
     * @param limit
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUncheckedAdmins")
    public ReturnClass<ArrayList<AdminGeneral>> getUnchechedGeneralAdmin(@PathParam("page") Integer page
            ,@PathParam("limit") Integer limit){
        System.out.println("page:"+page+"limit"+limit);
        ArrayList<AdminGeneral> admins = adminLoginRegisterMapper.selectUncheckedInfo(page-1,limit);
        System.out.println("查询到的admins为："+admins);
        return new ReturnClass<ArrayList<AdminGeneral>>(0,"",admins.size(),admins);
    }

    /**
     *
     * @param id
     * @param instruct 指令
     * @param email 邮箱用于通知是否通过审核
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/disposeAdminRegisterApply/{id}/{instruct}/{email}")
    public Boolean disposeAdminRegisterApply(@PathVariable("id") String id
            ,@PathVariable("instruct") String instruct
            ,@PathVariable("email") String email){
        System.out.println("fleaid为："+id+" 指令："+instruct+"email为："+email);
        if (instruct.equals("1")){
            return adminLoginRegisterMapper.updateAdminStatus(id);
        }else{
            return adminLoginRegisterMapper.deleteAdmin(id);
        }
    }

    /**
     * 查询所有已经通过审核的管理员
     * @param page
     * @param limit
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getCheckedAdmins")
    public ReturnClass<ArrayList<AdminGeneral>> getChechedGeneralAdmin(@PathParam("page") Integer page
            ,@PathParam("limit") Integer limit){
        System.out.println("page:"+page+"limit"+limit);
        ArrayList<AdminGeneral> admins = adminLoginRegisterMapper.selectCheckedInfo(page-1,limit);
        System.out.println("查询到的admins为："+admins);
        return new ReturnClass<ArrayList<AdminGeneral>>(0,"",admins.size(),admins);
    }
}

