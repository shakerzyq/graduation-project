package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zyq.graduation.management.pojo.AdminGeneral;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/4/27 20:32
 */

@Mapper
public interface SuperAdminMapper {

    @Select("select * from admin_general where status='3' limit #{page},#{limit}")
    ArrayList<AdminGeneral> selectUncheckedInfo(int page, Integer limit);

    @Select("select * from admin_general where status!='3' limit #{page},#{limit}")
    ArrayList<AdminGeneral> selectCheckedInfo(int page, Integer limit);

    @Update("update admin_general set status=#{status} where uuid=#{id}")
    Boolean updateAdminStatus(String id,String status);

    @Delete("delete from admin_general where uuid=#{id}")
    Boolean deleteAdmin(String id);

    @Select("select * from admin_general where status='3' and province=#{province} and college=#{college} and area=#{area} limit #{page},#{limit}")
    ArrayList<AdminGeneral> selectUncheckedInfo111(String province, String college, String area,Integer page,Integer limit);

    @Select("select * from admin_general where status='3' and province=#{province} and college=#{college} limit #{page},#{limit}")
    ArrayList<AdminGeneral> selectUncheckedInfo110(String province, String college,Integer page,Integer limit);

    @Select("select * from admin_general where status='3' and province=#{province} limit #{page},#{limit}")
    ArrayList<AdminGeneral> selectUncheckedInfo100(String province,Integer page,Integer limit);

    @Select("select * from admin_general where status='3' and name like #{name} limit #{page},#{limit}")
    ArrayList<AdminGeneral> selectUncheckedInfoByName(String name,Integer page,Integer limit);

    @Select("select * from admin_general where status!='3' and province=#{province} and college=#{college} and area=#{area} limit #{page},#{limit}")
    ArrayList<AdminGeneral> selectCheckedInfo111(String province, String college, String area,Integer page,Integer limit);

    @Select("select * from admin_general where status!='3' and province=#{province} and college=#{college} limit #{page},#{limit}")
    ArrayList<AdminGeneral> selectCheckedInfo110(String province, String college,Integer page,Integer limit);

    @Select("select * from admin_general where status!='3' and province=#{province} limit #{page},#{limit}")
    ArrayList<AdminGeneral> selectCheckedInfo100(String province,Integer page,Integer limit);

    @Select("select * from admin_general where status!='3' and name like #{name} limit #{page},#{limit}")
    ArrayList<AdminGeneral> selectCheckedInfoByName(String name,Integer page,Integer limit);
}
