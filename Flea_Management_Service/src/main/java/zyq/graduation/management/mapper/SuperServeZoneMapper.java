package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zyq.graduation.management.pojo.ServeZone;
import zyq.graduation.management.pojo.Services;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/5/3 16:42
 */
@Mapper
public interface SuperServeZoneMapper {

    @Select("select * from service_area limit #{page},#{limit}")
    ArrayList<Services> selectServiceZone(Integer page,Integer limit);

    @Update("update service_area set status=#{handleType} where uuid=#{zoneId}")
    Boolean updateServeZoneStatus(String zoneId, String handleType);

    @Select("select * from service_area where status!='1' and province=#{province} and college=#{college} and area=#{area} limit #{page},#{limit}")
    ArrayList<Services> selectServicedZone111(String province, String college, String area, Integer page, Integer limit);

    @Select("select * from service_area where status!='1' and province=#{province} and college=#{college} limit #{page},#{limit}")
    ArrayList<Services> selectServicedZone110(String province, String college, Integer page, Integer limit);

    @Select("select * from service_area where status!='1' and province=#{province} limit #{page},#{limit}")
    ArrayList<Services> selectServicedZone100(String province, Integer page, Integer limit);

    @Select("select * from service_area where status!='1' and college like #{college} limit #{page},#{limit}")
    ArrayList<Services> selectServicedZoneByName(String college, Integer page, Integer limit);

    @Select("select name from colleges where address=#{province}")
    ArrayList<String> selectCollegesOfProvince(String province);

    @Insert("insert into service_area (uuid,province,college,area) values (#{uuid},#{province},#{college},#{area})")
    Boolean insertServeZone(ServeZone serveZone);
}
