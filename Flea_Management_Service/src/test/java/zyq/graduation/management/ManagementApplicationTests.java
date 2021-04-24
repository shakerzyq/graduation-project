package zyq.graduation.management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zyq.graduation.management.mapper.AdminUserMapper;
import zyq.graduation.management.util.SendEmailMessage;

@SpringBootTest
class ManagementApplicationTests {

    @Autowired
    AdminUserMapper adminUserMapper;

    @Autowired
    SendEmailMessage sendEmailMessage;

    @Test
    void contextLoads() {
        sendEmailMessage.sendMessage("1312197142@qq.com","sf");
    }


}
