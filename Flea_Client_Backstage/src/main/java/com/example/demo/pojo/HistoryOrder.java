package com.example.demo.pojo;

import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/3/14 15:40
 */
@Component
public class HistoryOrder extends Order{
    private Order order;
    private String evaluate_content;
    private String evaluate_level;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getEvaluate_content() {
        return evaluate_content;
    }

    public void setEvaluate_content(String evaluate_content) {
        this.evaluate_content = evaluate_content;
    }

    public String getEvaluate_level() {
        return evaluate_level;
    }

    public void setEvaluate_level(String evaluate_level) {
        this.evaluate_level = evaluate_level;
    }
}
