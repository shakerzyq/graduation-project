package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/3/4 18:00
 */
@Component
public class ESGoodsTypes {
    private String type;
    private Boolean choice=false;
    private String bgcolor="#eee";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getChoice() {
        return choice;
    }

    public void setChoice(Boolean choice) {
        this.choice = false;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolorr(String bgcolor) {
        this.bgcolor = "#eee";
    }

    @Override
    public String toString() {
        return "ESGoodsTypes{" +
                "type='" + type + '\'' +
                ", choice=" + choice +
                ", bgclolr='" + bgcolor + '\'' +
                '}';
    }
}
