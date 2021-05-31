package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 * 提交商品举报修改信息
 * @author 周杨清
 * @date 2021/2/28 12:06
 */
@Component
public class GoodsReport {
    private String informer_id;
    private String violate_goods_id;
    private String violate_user_id;
    private String violate_content;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInformer_id() {
        return informer_id;
    }

    public void setInformer_id(String informer_id) {
        this.informer_id = informer_id;
    }

    public String getViolate_goods_id() {
        return violate_goods_id;
    }

    public void setViolate_goods_id(String violate_goods_id) {
        this.violate_goods_id = violate_goods_id;
    }

    public String getViolate_user_id() {
        return violate_user_id;
    }

    public void setViolate_user_id(String violate_user_id) {
        this.violate_user_id = violate_user_id;
    }

    public String getViolate_content() {
        return violate_content;
    }

    public void setViolate_content(String violate_content) {
        this.violate_content = violate_content;
    }

    @Override
    public String toString() {
        return "GoodsReport{" +
                "informer_id='" + informer_id + '\'' +
                ", violate_goods_id='" + violate_goods_id + '\'' +
                ", violate_user_id='" + violate_user_id + '\'' +
                ", violate_content='" + violate_content + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
