package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zyq.graduation.management.pojo.Report;
import zyq.graduation.management.pojo.ReportReturn;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/20 3:45
 */
@Mapper
public interface ReportMapper {

    @Select("select * from violate_user")
    ArrayList<Report> selectReport();
}
