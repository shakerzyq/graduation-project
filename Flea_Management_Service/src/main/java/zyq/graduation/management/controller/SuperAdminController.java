package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.SuperAdminMapper;
import zyq.graduation.management.pojo.AdminGeneral;
import zyq.graduation.management.pojo.Report;
import zyq.graduation.management.pojo.ReportReturn;
import zyq.graduation.management.pojo.ReturnClass;
import zyq.graduation.management.service.SuperAdminService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/4/27 20:27
 */
@RestController
@RequestMapping("/manageAdmin")
public class SuperAdminController {

    @Autowired
    SuperAdminMapper superAdminMapper;

    @Autowired
    SuperAdminService superAdminService;
    /**
     * 查询等待审核的管理员申请信息
     * @param page
     * @param limit
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUncheckedAdmins")
    public ReturnClass<ArrayList<AdminGeneral>> getUnchechedGeneralAdmin(@PathParam("page") Integer page
            , @PathParam("limit") Integer limit){
        System.out.println("page:"+page+"limit"+limit);
        ArrayList<AdminGeneral> admins = superAdminMapper.selectUncheckedInfo(page-1,limit);
        System.out.println("查询到的admins为："+admins);
        return new ReturnClass<ArrayList<AdminGeneral>>(0,"",admins.size(),admins);
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
        ArrayList<AdminGeneral> admins = superAdminMapper.selectCheckedInfo(page-1,limit);
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
            return superAdminMapper.updateAdminStatus(id,"0");
        }else{
            return superAdminMapper.deleteAdmin(id);
        }
    }


    /**
     * 获取未被审核的用户
     * @param page
     * @param limit
     * @param province 省份
     * @param college 大学
     * @param area 校区
     * @param name 用户名
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUnCheckedAdminByCondition")
    public ReturnClass<ArrayList<AdminGeneral>> selectUnCheckedInfoByCondition(@PathParam("page") Integer page
            , @PathParam("limit") Integer limit
            , @PathParam("province") String province
            , @PathParam("college") String college
            , @PathParam("area") String area
            , @PathParam("name") String name
    ){
        System.out.println(page);
        System.out.println(limit);
        System.out.println("province"+province);
        System.out.println("college"+college);
        System.out.println("area"+area);
        System.out.println("name"+name);
        ArrayList<AdminGeneral> admins = superAdminService.selectUnCheckedInfoByCondition(page-1,limit,province,college,area,name);
        System.out.println("查询到的结果"+admins);
        return new ReturnClass<ArrayList<AdminGeneral>>(0,"",admins.size(),admins);
    }

    /**
     * 获取审核通过的用户
     * @param page
     * @param limit
     * @param province 省份
     * @param college 大学
     * @param area 校区
     * @param name 用户名
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getCheckedAdminByCondition")
    public ReturnClass<ArrayList<AdminGeneral>> selectCheckedInfoByCondition(@PathParam("page") Integer page
            , @PathParam("limit") Integer limit
            , @PathParam("province") String province
            , @PathParam("college") String college
            , @PathParam("area") String area
            , @PathParam("name") String name
    ){
        System.out.println(page);
        System.out.println(limit);
        System.out.println("province"+province);
        System.out.println("college"+college);
        System.out.println("area"+area);
        System.out.println("name"+name);
        ArrayList<AdminGeneral> admins = superAdminService.selectCheckedInfoByCondition(page-1,limit,province,college,area,name);
        System.out.println("查询到的结果"+admins);
        return new ReturnClass<ArrayList<AdminGeneral>>(0,"",admins.size(),admins);
    }

    /**
     * 处理管理员
     * @param adminId
     * @param type
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/banadmin/{adminId}/{handleType}")
    public Boolean BanAdmin(@PathVariable("adminId") String adminId,@PathVariable("handleType") String type){
        System.out.println("id为："+adminId+"type为："+type);
        return superAdminMapper.updateAdminStatus(adminId,type);
    }
}
