package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author 周杨清
 * @date 2021/4/27 15:20
 */
@Mapper
public interface AdminServiceInfoMapper {

    @Select("select province from service_area")
    HashSet<String> selectProvince();

    @Select("select college from service_area where province=#{province}")
    HashSet<String> selectCollegeByProvince(String province);

    @Select("select area from service_area where college=#{college}")
    ArrayList<String> selectAreaByCollege(String college);
}
