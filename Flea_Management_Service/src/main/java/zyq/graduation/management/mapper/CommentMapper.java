package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zyq.graduation.management.pojo.CommentReport;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/27 15:59
 */
@Mapper
public interface CommentMapper {
    @Update("update violate_comment set status=#{status},end_time=now() where complain_id=#{complainId}")
    Boolean updataReportStatus(String complainId, Integer status);

    @Select("select * from violate_comment where status!=2 limit #{page},#{limit}")
    ArrayList<CommentReport> selectAccomplishReport(Integer page, Integer limit);

    @Select("select * from violate_comment where status=#{status} limit #{page},#{limit}")
    ArrayList<CommentReport> selectAccomplishReportStatus(Integer page, Integer limit, String status);

    @Select("select * from violate_comment where status!=2 and (informer_id=#{userId} or violate_user_id=#{userId}) limit #{page},#{limit}")
    ArrayList<CommentReport> selectAccomplishReportById(Integer page, Integer limit, String userId);

    @Select("select * from violate_comment where status=#{status} and (informer_id=#{userId} or violate_user_id=#{userId}) limit #{page},#{limit}")
    ArrayList<CommentReport> selectAccomplishReportByIdByStatus(Integer page, Integer limit, String userId, String status);
}