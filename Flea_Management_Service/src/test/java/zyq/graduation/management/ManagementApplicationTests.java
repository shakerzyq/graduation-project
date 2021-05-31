package zyq.graduation.management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zyq.graduation.management.mapper.AdminServiceInfoMapper;
import zyq.graduation.management.mapper.AdminUserMapper;
import zyq.graduation.management.util.MakeMD5;
import zyq.graduation.management.util.SendEmailMessage;

@SpringBootTest
class ManagementApplicationTests {

    @Autowired
    AdminUserMapper adminUserMapper;

    @Autowired
    SendEmailMessage sendEmailMessage;

    @Autowired
    AdminServiceInfoMapper adminServiceInfoMapper;

    @Test
    void contextLoads() {
        sendEmailMessage.sendMessage("1312197142@qq.com","sf");
    }

    @Test
    void TestGetProvince(){
        System.out.println("查到的结果为："+adminServiceInfoMapper.selectProvince());
    }

    @Test
    void TestGetArea(){
        String area ="helloworld";
        String[] areas = area.split("-");
        for(String area1:areas){
            System.out.println("结果是："+area1);
        }
    }

    @Test
    void TestMd5(){
        System.out.println(MakeMD5.makeMd5("123456"));
    }
}
