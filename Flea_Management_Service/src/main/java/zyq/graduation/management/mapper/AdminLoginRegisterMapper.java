package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.*;
import zyq.graduation.management.pojo.AdminGeneral;
import zyq.graduation.management.pojo.AdminSuper;
import zyq.graduation.management.pojo.User;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/28 20:06
 */
@Mapper
public interface AdminLoginRegisterMapper {

    @Select("select * from admin_general where password=#{password} and account=#{account}")
    AdminGeneral selectGeneralAdmin(AdminGeneral admin);

    @Select("select name from colleges where address=#{province}")
    ArrayList<String> selectCollegeByProvince(String province);

    @Select("select area from service_area where college=#{college}")
    ArrayList<String> selectAreaByCollege(String college);

    @Insert("insert into admin_general (uuid,name,stu_num,account,password,weixin,picture,province,college,area,status) " +
            "values (#{uuid},#{name},#{stu_num},#{account},#{password},#{weixin},#{picture},#{province},#{college},#{area},3)")
    Boolean insertGeneralAdmin(AdminGeneral adminGeneral);

    @Select("select count(*) from admin_general where college=#{college} and area=#{area} and stu_num=#{stu_num}")
    int verifyAdminInfo(AdminGeneral adminGeneral);

    @Select("select count(*) from admin_general where account=#{account}")
    int verifyAdminAccount(AdminGeneral adminGeneral);

    @Select("select count(*) from admin_super where account=#{account} and password=#{password}")
    Integer selectSuperAdmin(AdminSuper admin);






}
