package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zyq.graduation.management.pojo.CommentReport;
import zyq.graduation.management.pojo.Report;
import zyq.graduation.management.pojo.ReportReturn;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/20 3:45
 */
@Mapper
public interface ReportMapper {

    @Select("select * from violate_order where complain_status=2 limit #{page},#{limit}")
    ArrayList<Report> selectReport(Integer page, Integer limit);

    @Update("update violate_order set complain_status=1,audit_result=#{content} where complain_id=#{reportId}")
    Boolean receiveReport(String reportId,String content);

    @Delete("update violate_order set complain_status=0,audit_result=#{content} where complain_id=#{reportId}")
    Boolean refuseReport(String reportId,String content);

    /**
     * 查询处理完成的举报
     * @return
     */
    @Select("select * from violate_order where complain_status!=2 limit #{page},#{limit}")
    ArrayList<Report> selectAccomplishReport(Integer page, Integer limit);

    @Select("select * from violate_order where complain_status!=2  complain_type=#{reportType} limit #{page},#{limit}")
    ArrayList<Report> selectAccomplishReport1(Integer page, Integer limit,Integer reportType);

    @Select("select * from violate_order where  complain_status=#{resultType} limit #{page},#{limit}")
    ArrayList<Report> selectAccomplishReport2(Integer page, Integer limit,Integer resultType);

    @Select("select * from violate_order where  complain_type=#{reportType} and complain_status=#{resultType} limit #{page},#{limit}")
    ArrayList<Report> selectAccomplishReport3(Integer page, Integer limit,Integer reportType,Integer resultType);


    /**
     * 查询未处理的举报
     * @param type
     * @param goodsId
     * @param flea_id
     * @return
     */
    @Select("select * from violate_order where complain_status=2 and complain_type=#{type} " +
            "or (complain_userid=#{flea_id} or complained_userid=#{flea_id}) " +
            "and (goods_id=#{goodsId})")
    ArrayList<Report> selectReportSecond(Integer type,String goodsId,String flea_id);


    @Select("select * from violate_order where complain_status=2 and " +
            " complain_type=#{type} limit #{page},#{limit}")
    ArrayList<Report> selectReport000_100(Integer page, Integer limit,String type);

    @Select("select * from violate_order where complain_status=2 and complain_type=#{type} and" +
            " (complain_userid=#{flea_id} or complained_userid=#{flea_id}) limit #{page},#{limit}")
    ArrayList<Report> selectReport001(Integer page, Integer limit,String type,String flea_id);

    @Select("select * from violate_order where complain_status=2 and complain_type=#{type} and" +
            " goods_id=#{goodsId} limit #{page},#{limit}")
    ArrayList<Report> selectReport010(Integer page, Integer limit,String type,String goodsId);

    @Select("select * from violate_order where complain_status=2 and complain_type=#{type} and" +
            " (complain_userid=#{flea_id} or complained_userid=#{flea_id}) and" +
            " goods_id=#{goodsId} limit #{page},#{limit}")
    ArrayList<Report> selectReport011(Integer page, Integer limit,String type,String goodsId, String flea_id);

    @Select("select * from violate_order  where complain_status=2 and " +
            "(complain_userid=#{flea_id} or complained_userid=#{flea_id}) limit #{page},#{limit}")
    ArrayList<Report> selectReport201(Integer page, Integer limit,String flea_id);

    @Select("select * from violate_order  where complain_status=2 and " +
            "goods_id=#{goodsId} limit #{page},#{limit}")
    ArrayList<Report> selectReport210(Integer page, Integer limit,String goodsId);

    @Select("select * from violate_order  where complain_status=2 and " +
            "(complain_userid=#{flea_id} or complained_userid=#{flea_id}) and " +
            "goods_id=#{goodsId} limit #{page},#{limit}")
    ArrayList<Report> selectReport211(Integer page, Integer limit,String goodsId, String flea_id);

    @Select("select * from violate_order where complain_status=2 and complain_type=#{type} and (complain_userid=#{flea_id} or complained_userid=#{flea_id}) ")
    ArrayList<Report> selectReportSecond1(Integer type, String flea_id);

    @Select("select * from violate_order where complain_status=2 and complain_type=#{type} and goods_id=#{goodsId}")
    ArrayList<Report> selectReportSecond2(Integer type, String goodsId);

    @Select("select * from violate_order  where complain_status=2 and complain_type=#{type}")
    ArrayList<Report> selectReportSecond3(Integer type);

    @Select("select * from violate_comment where status=2 and (informer_id=#{userId} or violate_user_id=#{userId}) limit #{page},#{limit}")
    ArrayList<CommentReport> selectCommentsReportById(Integer page, Integer limit, String userId);

    @Select("select * from violate_comment where status=2 limit #{page},#{limit}")
    ArrayList<CommentReport> selectCommentsReport(Integer page, Integer limit);

    @Select("select * from violate_comment where status=2 and  violate_user_id=#{userId} limit #{page},#{limit}")
    ArrayList<CommentReport> selectCommentsReportComplainedById(Integer page, Integer limit, String userId, String type);
    @Select("select * from violate_comment where status=2 and informer_id=#{userId} limit #{page},#{limit}")
    ArrayList<CommentReport> selectCommentsReportComplainById(Integer page, Integer limit, String userId, String type);
}
