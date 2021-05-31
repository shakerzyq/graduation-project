package zyq.graduation.management.service.impl;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyq.graduation.management.mapper.SuperServeZoneMapper;
import zyq.graduation.management.pojo.AdminGeneral;
import zyq.graduation.management.pojo.Services;
import zyq.graduation.management.service.SuperServeZoneService;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/5/3 16:50
 */
@Service
public class ISuperServeZoneService implements SuperServeZoneService {

    @Autowired
    SuperServeZoneMapper superServeZoneMapper;



    @Override
    public ArrayList<Services> getServices(Integer page, Integer limit) {
        ArrayList<Services> services = superServeZoneMapper.selectServiceZone(page,limit);
//        ArrayList<Services> serviceList = new ArrayList<>();
        //            serviceList.add()
//        serviceList.addAll(services);
        return services;
    }

    @Override
    public ArrayList<Services> selectCheckedInfoByCondition(Integer page, Integer limit, String province, String college, String area, String collegename) {
        String judgeStr = "";
        judgeStr+=province.equals("省份")?"0":"1";
        judgeStr+=college.equals("学校")?"0":"1";
        judgeStr+=area.equals("校区")?"0":"1";
        if (collegename==null||collegename.equals("")){
            switch (judgeStr){
                case "111":
                    return superServeZoneMapper.selectServicedZone111(province,college,area,page,limit);
                case "110":
                    return superServeZoneMapper.selectServicedZone110(province,college,page,limit);
                case "100":
                    return superServeZoneMapper.selectServicedZone100(province,page,limit);
                case "000":
                    return superServeZoneMapper.selectServiceZone(page,limit);
                default:

            }
        }else{
            return superServeZoneMapper.selectServicedZoneByName("%"+collegename+"%",page,limit);
        }

        return null;
    }
}
