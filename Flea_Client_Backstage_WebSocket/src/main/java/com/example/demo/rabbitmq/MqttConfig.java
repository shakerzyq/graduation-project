package com.example.demo.rabbitmq;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/2/24 14:02
 */
@Component
@Data
public class MqttConfig {

    /**
     * RabbitMQ连接用户名
     */
    private String username;
    /**
     * RabbitMQ连接密码
     */
    private String password;
    /**
     * RabbitMQ的MQTT默认topic
     */
    private String defaultTopic;
    /**
     * RabbitMQ的MQTT连接地址
     */
    private String url;

    public String getUsername() {
        return "guest";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return "guest";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDefaultTopic() {
        return "testTopic";
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    public String getUrl() {
        return "tcp://116.62.149.46:1883";
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
