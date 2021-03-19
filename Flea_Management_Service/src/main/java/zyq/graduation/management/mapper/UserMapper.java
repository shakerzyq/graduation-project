package zyq.graduation.management.mapper;

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
public interface UserMapper {
    @Select("select * from user where status='待审核'")
    ArrayList<User> selectUncheckedUser();

    @Update("update user set status='正常' where flea_id=#{flea_id}")
    Boolean updateUserStatus(String flea_id);

    @Select("select * from user where flea_id=#{flea_id}")
    User selectUserDetail(String flea_id);

    @Select("select * from user where status='待审核' and nickname like #{nickname}")
    ArrayList<User> selectLikeUncheckedUser(String nickname);

    @Select("select * from user where status!='待审核'")
    ArrayList<User> selectCheckedUser();

    /**
     * 封禁
     * @param flea_id
     * @return
     */
    @Update("update user set status='封禁' where flea_id=#{flea_id}")
    Boolean BanUser(String flea_id);

    /**
     * 解封
     * @param flea_id
     * @return
     */
    @Update("update user set status='正常' where flea_id=#{flea_id}")
    Boolean UnBanUser(String flea_id);

    @Select("select * from user where status!='未审核' and nickname like #{nickname}")
    ArrayList<User> selectLikeCheckedUser(String s);
}
