package zyq.graduation.management.pojo;

/**
 * 商品举报单实体类
 * @author 周杨清
 * @date 2021/5/6 16:32
 */
public class GoodsReport {

    private String complain_id;
    private String informer_id;
    private String violate_goods_id;
    private String violate_user_id;
    private String violate_content;
    private Integer status;
    private String start_time;
    private String end_time;
    private String comment;
    private String admin_id;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComplain_id() {
        return complain_id;
    }

    public void setComplain_id(String complain_id) {
        this.complain_id = complain_id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    @Override
    public String toString() {
        return "GoodsReport{" +
                "complain_id='" + complain_id + '\'' +
                ", informer_id='" + informer_id + '\'' +
                ", violate_goods_id='" + violate_goods_id + '\'' +
                ", violate_user_id='" + violate_user_id + '\'' +
                ", violate_content='" + violate_content + '\'' +
                ", status=" + status +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", comment='" + comment + '\'' +
                ", admin_id='" + admin_id + '\'' +
                '}';
    }
}
