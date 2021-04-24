package zyq.graduation.management.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zyq.graduation.management.pojo.User;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/18 23:32
 */
@Mapper
public interface AdminUserMapper {
    @Select("select a.*,b.status from user a join user_status b on a.flea_id=b.flea_id " +
            "where b.status=3 limit #{page},#{limit}")
    ArrayList<User> selectUncheckedUser(Integer page,Integer limit);

    @Update("update user_status set status=0 where flea_id=#{flea_id} ")
    Boolean updateUserStatus(String flea_id);

    @Select("select a.*,b.status,b.deadline from user a join user_status b on a.flea_id=b.flea_id " +
            "where b.status!=3 and a.flea_id=#{flea_id} ")
    User selectUserDetail(String flea_id);

    @Select("select a.*,b.status,b.deadline from user a join user_status b on a.flea_id=b.flea_id " +
            "where b.status=3 like #{nickname} limit #{page},#{limit}")
    ArrayList<User> selectLikeUncheckedUser(String nickname,Integer page,Integer limit);

    @Select("select a.*,b.status,b.deadline from user a join user_status b on a.flea_id=b.flea_id " +
            "where b.status!=3 limit #{page},#{limit}")
    ArrayList<User> selectCheckedUser(Integer page,Integer limit);



    @Select("select a.*,b.status,b.deadline from user a join user_status b on a.flea_id=b.flea_id " +
            "where b.status!=3 like #{nickname} limit #{page},#{limit}")
    ArrayList<User> selectLikeCheckedUser(String nickname,Integer page,Integer limit);

    @Update("update user_status set status=#{status} where flea_id=#{flea_id}")
    Boolean updateBanstatus( Integer status,String flea_id);

    @Update("update user_status set deadline=#{deadline},status=2 where flea_id=#{flea_id}")
    Boolean updateDeadline(long deadline, String flea_id);

    @Select("select * from user where flea_id=#{flea_id}")
    User selectUser(String flea_id);

    @Delete("delete from user where flea_id=#{flea_id}")
    Boolean deleteUser(String flea_id);

    @Update("update user set credit=credit-#{credit} where flea_id=#{flea_id}")
    Boolean updateUserCredit(String flea_id, String credit);
}
