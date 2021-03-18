package zyq.graduation.management.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/19 0:01
 */
public class UserReturn implements Serializable {
    private Integer code;
    private String msg;
    private Integer count;
    private ArrayList<User> data;

    public UserReturn(Integer code, String msg, Integer count, ArrayList<User> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<User> getData() {
        return data;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }
}
