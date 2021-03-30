package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zyq.graduation.management.pojo.AdminGeneral;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/28 20:06
 */
@Mapper
public interface LoginRegisterMapper {

    @Select("select count(*) from admin_general where password=#{password} and account=#{account}")
    Integer selectGeneralAdmin(AdminGeneral admin);

    @Select("select name from colleges where address=#{province}")
    ArrayList<String> selectCollegeByProvince(String province);

    @Select("select area from service_area where college=#{college}")
    ArrayList<String> selectAreaByCollege(String college);

    @Insert("insert into admin_general (uuid,name,stu_num,account,password,weixin,province,college,area,status) " +
            "values (#{uuid},#{name},#{stu_num},#{account},#{password},#{weixin},#{province},#{college},#{area},#{status})")
    Boolean insertGeneralAdmin(AdminGeneral adminGeneral);

    @Select("select count(*) from admin_general where college=#{college} and area=#{area} and stu_num=#{stu_num}")
    int verifyAdminInfo(AdminGeneral adminGeneral);

    @Select("select count(*) from admin_general where account=#{account}")
    int verifyAdminAccount(AdminGeneral adminGeneral);
}
