package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.UserMapper;
import zyq.graduation.management.pojo.User;
import zyq.graduation.management.pojo.UserReturn;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/18 23:25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    /**
     * 查询注册申请信息
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUncheckedUsers")
    public UserReturn getUncheckedUser(){
        ArrayList<User> users = userMapper.selectUncheckedUser();
        return new UserReturn(0,"",users.size(),users);
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/disposeRegisterApply/{flea_id}/{instruct}")
    public Boolean disposeRegisterApply(@PathVariable("flea_id") String flea_id,@PathVariable("instruct") String instruct){
        System.out.println("fleaid为："+flea_id+" 指令："+instruct);
        if (instruct.equals("1")){
            return userMapper.updateUserStatus(flea_id);
        }else{
            return true;
        }
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUserDetail/{flea_id}")
    public User getUserDetail(@PathVariable("flea_id") String flea_id){
        return userMapper.selectUserDetail(flea_id);
    }
}
