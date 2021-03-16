package com.example.demo.mapper;

import com.example.demo.pojo.Account;
import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Mapper
@Component
public interface RegisterMapper {

    /**
     * 查询省份对应的大学
     * @param province
     * @return
     */
    @Select("select name from colleges where address = #{province}")
    public ArrayList<String> getColleges(String province);

    @Insert("insert into account (flea_id,email,password) values (#{flea_id},#{email},#{password})")
    public Boolean InsertAccount(Account account);
   /* @Insert("insert into account(id ,flea_id,email,pwd,status) values (1,#{flea_id},{#email},#{pwd},#{status})")
    public Boolean InsertAccount(String flea_id,String email,String pwd,String status);*/

    @Insert("insert into user (flea_id,sex,nickname,name,stu_num,province,college,academy,grade,user_icon,testify_img,self_introduction,status,credit,weixin) " +
            "values (#{flea_id},#{sex},#{nickname},#{name},#{stu_num},#{province},#{college},#{academy},#{grade},#{user_icon},#{testify_img},#{self_introduction},#{status},#{credit},#{weixin})")
    public Boolean InsertUser(User user);

    @Select("select * from account where email=#{email}")
    public Account SelectAccount(String email);

}
