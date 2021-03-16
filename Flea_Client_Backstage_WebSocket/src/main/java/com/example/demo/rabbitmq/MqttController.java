package com.example.demo.rabbitmq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 周杨清
 * @date 2021/2/24 14:17
 */
@Api(tags = "MqttController", description = "MQTT测试接口")
@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttGateway mqttGateway;
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/sendToDefaultTopic")
    @ApiOperation("向默认主题发送消息")
    public String sendToDefaultTopic(String payload) {
        mqttGateway.sendToMqtt(payload);
        return "success";
    }
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/sendToTopic/{payload}/{topic}")
    @ApiOperation("向指定主题发送消息")
    public String  sendToTopic(@PathVariable("payload") String payload,@PathVariable("topic") String topic) {
        System.out.println("payLoad:"+payload+" topic:"+topic);
        mqttGateway.sendToMqtt(payload, topic);
        return "success";
    }

}
