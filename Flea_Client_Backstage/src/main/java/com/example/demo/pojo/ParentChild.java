package com.example.demo.pojo;

/**
 * 评论父子表
 * @author 周杨清
 * @date 2021/2/23 16:24
 */
public class ParentChild {

    private String parent_id;
    private String child_id;

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    @Override
    public String toString() {
        return "ParentChild{" +
                "parent_id='" + parent_id + '\'' +
                ", child_id='" + child_id + '\'' +
                '}';
    }
}
