package zyq.graduation.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyq.graduation.management.mapper.SuperAdminMapper;
import zyq.graduation.management.pojo.AdminGeneral;
import zyq.graduation.management.service.SuperAdminService;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/4/27 21:08
 */
@Service
public class ISuperAdminService implements SuperAdminService {

    @Autowired
    SuperAdminMapper superAdminMapper;

    @Override
    public ArrayList<AdminGeneral> selectUnCheckedInfoByCondition(Integer page, Integer limit, String province, String college, String area, String name) {
        String judgeStr = "";
        judgeStr+=province.equals("省份")?"0":"1";
        judgeStr+=college.equals("学校")?"0":"1";
        judgeStr+=area.equals("校区")?"0":"1";
        if (name==null||name.equals("")){
            switch (judgeStr){
                case "111":
                    return superAdminMapper.selectUncheckedInfo111(province,college,area,page,limit);
                case "110":
                    return superAdminMapper.selectUncheckedInfo110(province,college,page,limit);
                case "100":
                    return superAdminMapper.selectUncheckedInfo100(province,page,limit);
                case "000":
                    return superAdminMapper.selectUncheckedInfo(page,limit);
                default:

            }
        }else{
            return superAdminMapper.selectUncheckedInfoByName("%"+name+"%",page,limit);
        }

        return null;
    }

    @Override
    public ArrayList<AdminGeneral> selectCheckedInfoByCondition(Integer page, Integer limit, String province, String college, String area, String name) {
        String judgeStr = "";
        judgeStr+=province.equals("省份")?"0":"1";
        judgeStr+=college.equals("学校")?"0":"1";
        judgeStr+=area.equals("校区")?"0":"1";
        if (name==null||name.equals("")){
            switch (judgeStr){
                case "111":
                    return superAdminMapper.selectCheckedInfo111(province,college,area,page,limit);
                case "110":
                    return superAdminMapper.selectCheckedInfo110(province,college,page,limit);
                case "100":
                    return superAdminMapper.selectCheckedInfo100(province,page,limit);
                case "000":
                    return superAdminMapper.selectCheckedInfo(page,limit);
                default:

            }
        }else{
            return superAdminMapper.selectCheckedInfoByName("%"+name+"%",page,limit);
        }

        return null;
    }
}
