package com.example.demo.pojo;

/**
 * @author 周杨清
 * @date 2021/3/16 21:06
 */
public class BrowseLog {
    private String browse_id;
    private String user_id;
    private String goods_id;
    private String browse_date;
    private String browse_num;

    public String getBrowse_id() {
        return browse_id;
    }

    public void setBrowse_id(String browse_id) {
        this.browse_id = browse_id;
    }

    public String getBrowse_date() {
        return browse_date;
    }

    public void setBrowse_date(String browse_date) {
        this.browse_date = browse_date;
    }

    public String getBrowse_num() {
        return browse_num;
    }

    public void setBrowse_num(String browse_num) {
        this.browse_num = browse_num;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    @Override
    public String toString() {
        return "BrowseLog{" +
                "browse_id='" + browse_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", browse_date='" + browse_date + '\'' +
                ", browse_num='" + browse_num + '\'' +
                '}';
    }
}
