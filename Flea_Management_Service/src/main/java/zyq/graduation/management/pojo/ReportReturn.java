package zyq.graduation.management.pojo;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/20 3:44
 */
public class ReportReturn {

    private Integer code;
    private String msg;
    private Integer count;
    private ArrayList<Report> data;

    @Override
    public String toString() {
        return "ReportReturn{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
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

    public ArrayList<Report> getData() {
        return data;
    }

    public void setData(ArrayList<Report> data) {
        this.data = data;
    }

    public ReportReturn(Integer code, String msg, Integer count, ArrayList<Report> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
}
