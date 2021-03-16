package com.example.demo.pojo.putpojo;

import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/3/15 18:56
 */
@Component
public class UserComplainHistory {

    private String complain_id;
    private String complain_userid;
    private String complained_userid;
    private String complain_content;
    private String complain_type;
    private String complain_photos;
    private String audit_result;
    private String audit_content;
    private String complain_status;

    public String getAudit_result() {
        return audit_result;
    }

    public void setAudit_result(String audit_result) {
        this.audit_result = audit_result;
    }

    public String getAudit_content() {
        return audit_content;
    }

    public void setAudit_content(String audit_content) {
        this.audit_content = audit_content;
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

    public String getComplain_type() {
        return complain_type;
    }

    public void setComplain_type(String complain_type) {
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
}
