package com.example.demo.pojo;

public class Recommend {
    private Integer id;
    private String image;
    private String oldprice;
    private String description;
    private String nowprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(String oldprice) {
        this.oldprice = oldprice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNowprice() {
        return nowprice;
    }

    public void setNowprice(String nowprice) {
        this.nowprice = nowprice;
    }
}
