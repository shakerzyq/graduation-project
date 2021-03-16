package com.example.demo.rabbitmq;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author 周杨清
 * @date 2021/2/24 14:16
 */
@Component
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    /**
     * 发送消息到默认topic
     */
    void sendToMqtt(String payload);

    /**
     * 发送消息到指定topic
     */
    void sendToMqtt(String payload, @Header(MqttHeaders.TOPIC) String topic);

    /**
     * 发送消息到指定topic并设置QOS
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);
}
