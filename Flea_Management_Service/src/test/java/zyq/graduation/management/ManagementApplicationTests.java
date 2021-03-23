package zyq.graduation.management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zyq.graduation.management.mapper.UserMapper;
import zyq.graduation.management.pojo.User;
import zyq.graduation.management.util.SendEmailMessage;

import java.util.ArrayList;

@SpringBootTest
class ManagementApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SendEmailMessage sendEmailMessage;

    @Test
    void contextLoads() {
        sendEmailMessage.sendMessage("1312197142@qq.com","sf");
    }

}
