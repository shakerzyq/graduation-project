package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.SuperServeZoneMapper;
import zyq.graduation.management.pojo.AdminGeneral;
import zyq.graduation.management.pojo.ReturnClass;
import zyq.graduation.management.pojo.ServeZone;
import zyq.graduation.management.pojo.Services;
import zyq.graduation.management.service.SuperServeZoneService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author 周杨清
 * @date 2021/5/3 16:36
 */
@RestController
@RequestMapping("/manageServicedZone")
public class SuperServeZoneController {

    @Autowired
    SuperServeZoneMapper superServeZoneMapper;

    @Autowired
    SuperServeZoneService superServeZoneService;


    /**
     * 获取所有服务区域
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getServices")
    public ReturnClass<ArrayList<Services>> getServices(
            @PathParam("page") Integer page
            , @PathParam("limit") Integer limit
    ){

        ArrayList<Services> services = superServeZoneService.getServices(page-1,limit);

        return new ReturnClass<ArrayList<Services>>(0,"",1,services);
    }

    /**
     * 管理服务区域
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/handleServeZone/{zoneId}/{handleType}")
    public Boolean handleService(@PathVariable("zoneId") String zoneId,@PathVariable("handleType") String handleType){
        System.out.println("zoneId为："+zoneId+"handleType:"+handleType);
        return superServeZoneMapper.updateServeZoneStatus(zoneId,handleType);
    }

    /**
     * 通过条件查询服务院校
     * @param page
     * @param limit
     * @param province
     * @param college
     * @param area
     * @param name
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/SearchServeZone")
    public ReturnClass<ArrayList<Services>> getServeZonesByCondition(@PathParam("page") Integer page
            , @PathParam("limit") Integer limit
            , @PathParam("province") String province
            , @PathParam("college") String college
            , @PathParam("area") String area
            , @PathParam("name") String name){
        System.out.println(page);
        System.out.println(limit);
        System.out.println("province"+province);
        System.out.println("college"+college);
        System.out.println("area"+area);
        System.out.println("name"+name);
        ArrayList<Services> services = superServeZoneService.selectCheckedInfoByCondition(page-1,limit,province,college,area,name);
        System.out.println("查询到的结果"+services);
        return new ReturnClass<ArrayList<Services>>(0,"",1,services);
    }

    /**
     * 查询省份的学校
     * @param province
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getCollegesOfProvince/{province}")
    public ArrayList<String> getCollegesOfProvince(@PathVariable("province") String province){
        return superServeZoneMapper.selectCollegesOfProvince(province);
    }

    /**
     * 添加服务区域
     * @param serveZone
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/addServeZone")
    public Boolean addServeZone(@RequestBody ServeZone serveZone){
        System.out.println("添加的服务区域为"+serveZone);
        serveZone.setUuid(UUID.randomUUID().toString());
        return superServeZoneMapper.insertServeZone(serveZone);
    }
}
