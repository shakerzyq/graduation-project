package com.example.demo.mapper;

import com.example.demo.pojo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;


@Mapper
@Component
public interface SwipperMapper {
    @Select("Select * from imageswp")
    public ArrayList<ImageSwipper> getImageSwp();

    @Select("Select * from recommend limit #{start},#{end}")
    public ArrayList<Recommend> getGoods(Integer start,Integer end);

    @Select("Select * from pictures")
    public ArrayList<PictureType> getPicType();

    @Select("Select * from images where typeid=#{id}")
    public ArrayList<Images> getImages(Integer id);

    @Select("Select * from news")
    public ArrayList<News> getNews();

    @Insert("insert into colleges (name,academy_type,school_type,address,rank,phone,contact_address,ascription,site) " +
            " values (#{name},#{academy_type},#{school_type},#{address},#{rank},#{phone},#{contact_address},#{ascription},#{site})")
    public boolean InsertCollege(String name,String academy_type,String school_type,String address,String rank,String phone,String contact_address,String ascription,String site);

    @Select("select name from colleges where address = #{province}")
    public ArrayList<String> getColleges(String province);
}
