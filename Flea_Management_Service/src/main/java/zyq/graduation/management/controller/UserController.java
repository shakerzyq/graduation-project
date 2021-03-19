package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.mapper.UserMapper;
import zyq.graduation.management.pojo.User;
import zyq.graduation.management.pojo.UserReturn;

import javax.websocket.server.PathParam;
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


//    @CrossOrigin(origins = "*",maxAge = 3600)
//    @GetMapping("/getUncheckedUsers/{nickname}")
//    public UserReturn getUncheckedUser(@PathVariable("nickname") String nickname){
//        ArrayList<User> users = null;
//        if (nickname.equals("name")){
//            users = userMapper.selectUncheckedUser();
//        }else {
//            users = userMapper.selectLikeUncheckedUser("%"+nickname+"%");
//        }
//        return new UserReturn(0,"",users.size(),users);
//    }
    /**
     * 查询注册申请信息
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUncheckedUsers")
    public UserReturn getUncheckedUser(@PathParam("page") String page
            ,@PathParam("limit") String limit){
        System.out.println("page:"+page+"limit"+limit);
        ArrayList<User> users = null;
            users = userMapper.selectUncheckedUser();
//        users = userMapper.selectLikeUncheckedUser("%"+nickname+"%");
        System.out.println(users);
        return new UserReturn(0,"",users.size(),users);
    }

    /**
     * 获取所有审核通过的用户
     * @param page
     * @param limit
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getCheckedUsers")
    public UserReturn getCheckedUsers(@PathParam("page") String page
            ,@PathParam("limit") String limit){
        System.out.println("page:"+page+"limit"+limit);
        ArrayList<User> users = null;
        users = userMapper.selectCheckedUser();
//        users = userMapper.selectLikeUncheckedUser("%"+nickname+"%");
        System.out.println(users);
        return new UserReturn(0,"",users.size(),users);
    }


    /**
     * 模糊查询注册申请信息
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUncheckedUsersByName")
    public UserReturn getUncheckedUserByname(
            @PathParam("page") String page
            ,@PathParam("limit") String limit
            ,@PathParam("nickname") String nickname){
        System.out.println("page:"+page+"limit"+limit+"nickname:"+nickname);
        ArrayList<User> users = userMapper.selectLikeUncheckedUser("%"+nickname+"%");

        System.out.println(users);
        return new UserReturn(0,"",users.size(),users);
    }

    /**
     * 模糊查询通过注册信息
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getCheckedUsersByName")
    public UserReturn getCheckedUserByname(
            @PathParam("page") String page
            ,@PathParam("limit") String limit
            ,@PathParam("nickname") String nickname){
        System.out.println("page:"+page+"limit"+limit+"nickname:"+nickname);
        ArrayList<User> users = userMapper.selectLikeCheckedUser("%"+nickname+"%");

        System.out.println(users);
        return new UserReturn(0,"",users.size(),users);
    }

    /**
     * 处理注册申请
     * @param flea_id
     * @param instruct
     * @return
     */
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

    /**
     * 获取用户的详细信息
     * @param flea_id
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUserDetail/{flea_id}")
    public User getUserDetail(@PathVariable("flea_id") String flea_id){
        return userMapper.selectUserDetail(flea_id);
    }

    /**
     * 改变用户封禁状态
     * @param flea_id
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/updateUserStatus/{order}/{flea_id}")
    public Boolean updateUserStatus(@PathVariable("order") String order,@PathVariable("flea_id") String flea_id){
        System.out.println("order:"+order+"flea_id:"+flea_id);
        if (order.equals("ban")){
            return userMapper.BanUser(flea_id);
        }else {
            return userMapper.UnBanUser(flea_id);
        }
    }

//    public UserReturn
}
