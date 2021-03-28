package zyq.graduation.management.pojo;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author 周杨清
 * @date 2021/3/26 22:59
 */
@Component
public class CommentReport implements Serializable {
    private String complain_id;
    private Integer rank;
    private String informer_id;
    private String violate_comment_id;
    private String violate_user_id;
    private String content;
    private Integer status;
    private String start_time;
    private String end_time;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getViolate_comment_id() {
        return violate_comment_id;
    }

    public void setViolate_comment_id(String violate_comment_id) {
        this.violate_comment_id = violate_comment_id;
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


    public String getViolate_user_id() {
        return violate_user_id;
    }

    public void setViolate_user_id(String violate_user_id) {
        this.violate_user_id = violate_user_id;
    }

    @Override
    public String toString() {
        return "CommentReport{" +
                "complain_id='" + complain_id + '\'' +
                ", rank=" + rank +
                ", informer_id='" + informer_id + '\'' +
                ", violate_comment_id='" + violate_comment_id + '\'' +
                ", violate_user_id='" + violate_user_id + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                '}';
    }
}
