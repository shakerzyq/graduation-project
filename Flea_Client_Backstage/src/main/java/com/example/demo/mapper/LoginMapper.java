package com.example.demo.mapper;

import com.example.demo.pojo.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/2/19 12:34
 */
@Mapper
@Component
public interface LoginMapper {
    @Select("select * from account where password=#{password} and email = #{email}")
    public Account selectEmailByPwd(Account account);

    @Select("select flea_id from account where email = #{email}")
    public String selectFealId(String email);

    @Update("update account set password=#{password} where email=#{email}")
    Boolean updatePwd(String email, String password);

    @Select("select status from service_area where college=#{college} and area=#{area}")
    Integer selectServiceStatus(String college, String area);
}
