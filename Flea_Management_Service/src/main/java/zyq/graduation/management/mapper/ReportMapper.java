package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zyq.graduation.management.pojo.Report;
import zyq.graduation.management.pojo.ReportReturn;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/20 3:45
 */
@Mapper
public interface ReportMapper {

    @Select("select * from violate_user where complain_status='处理中'")
    ArrayList<Report> selectReport();

    @Update("update violate_user set complain_status='通过',audit_result=#{content} where complain_id=#{reportId}")
    Boolean receiveReport(String reportId,String content);

    @Delete("update violate_user set complain_status='拒绝',audit_result=#{content} where complain_id=#{reportId}")
    Boolean refuseReport(String reportId,String content);
}
