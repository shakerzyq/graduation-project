package com.example.demo.pojo.putpojo;

import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/3/15 1:12
 */
@Component
public class UserComplain {
    private String complain_id;
    private String order_id;
    private String goods_id;
    private String complain_userid;
    private String complained_userid;
    private String complain_content;
    private Integer complain_type;
    private String complain_photos;
    private String complain_status;
    private String start_time;
    private String end_time;
    private String audit_result;
    private String email;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAudit_result() {
        return audit_result;
    }

    public void setAudit_result(String audit_result) {
        this.audit_result = audit_result;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getComplain_type() {
        return complain_type;
    }

    public void setComplain_type(Integer complain_type) {
        this.complain_type = complain_type;
    }

    public String getComplain_photos() {
        return complain_photos;
    }

    public void setComplain_photos(String complain_photos) {
        this.complain_photos = complain_photos;
    }

    public String getComplain_status() {
        return complain_status;
    }

    public void setComplain_status(String complain_status) {
        this.complain_status = complain_status;
    }

    public String getComplain_id() {
        return complain_id;
    }

    public void setComplain_id(String complain_id) {
        this.complain_id = complain_id;
    }

    public String getComplain_userid() {
        return complain_userid;
    }

    public void setComplain_userid(String complain_userid) {
        this.complain_userid = complain_userid;
    }

    public String getComplained_userid() {
        return complained_userid;
    }

    public void setComplained_userid(String complained_userid) {
        this.complained_userid = complained_userid;
    }

    public String getComplain_content() {
        return complain_content;
    }

    public void setComplain_content(String complain_content) {
        this.complain_content = complain_content;
    }

    @Override
    public String toString() {
        return "UserComplain{" +
                "complain_id='" + complain_id + '\'' +
                ", order_id='" + order_id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", complain_userid='" + complain_userid + '\'' +
                ", complained_userid='" + complained_userid + '\'' +
                ", complain_content='" + complain_content + '\'' +
                ", complain_type=" + complain_type +
                ", complain_photos='" + complain_photos + '\'' +
                ", complain_status='" + complain_status + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
