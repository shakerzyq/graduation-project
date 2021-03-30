package com.example.demo.pojo.register;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 周杨清
 * @date 2021/3/30 23:59
 */
public class CollegeAreas implements Serializable {



    private String college;
    private List<String> area;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }
}
