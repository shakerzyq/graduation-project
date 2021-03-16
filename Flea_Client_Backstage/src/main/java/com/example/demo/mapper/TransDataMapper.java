package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author 周杨清
 * @date 2021/3/3 21:42
 */
@Mapper
@Component
public interface TransDataMapper {

    @Select("select * from ")
    ArrayList<String> selectGoodsInfo();
}
