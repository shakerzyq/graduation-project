package zyq.graduation.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zyq.graduation.management.dao.ESDataDao;
import zyq.graduation.management.mapper.GoodsMapper;
import zyq.graduation.management.mapper.OrderMapper;
import zyq.graduation.management.mapper.UserMapper;
import zyq.graduation.management.pojo.User;
import zyq.graduation.management.pojo.UserReturn;
import zyq.graduation.management.service.UserService;
import zyq.graduation.management.util.SendEmailMessage;

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

    @Autowired
    UserService userService;

    @Autowired
    SendEmailMessage sendEmailMessage;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ESDataDao esDataDao;
    /**
     * 查询注册申请信息
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getUncheckedUsers")
    public UserReturn getUncheckedUser(@PathParam("page") Integer page
            ,@PathParam("limit") Integer limit){
        System.out.println("page:"+page+"limit"+limit);
        ArrayList<User> users = null;
            users = userMapper.selectUncheckedUser(page-1,limit);
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
    public UserReturn getCheckedUsers(@PathParam("page") Integer page
            ,@PathParam("limit") Integer limit){
        System.out.println("page:"+page+"limit"+limit);
        ArrayList<User> users = null;
        users = userMapper.selectCheckedUser(page-1,limit);
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
            @PathParam("page") Integer page
            ,@PathParam("limit") Integer limit
            ,@PathParam("nickname") String nickname){
        System.out.println("page:"+page+"limit"+limit+"nickname:"+nickname);
        ArrayList<User> users = userMapper.selectLikeUncheckedUser("%"+nickname+"%",page,limit);

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
            @PathParam("page") Integer page
            ,@PathParam("limit") Integer limit
            ,@PathParam("nickname") String nickname){
        System.out.println("page:"+page+"limit"+limit+"nickname:"+nickname);
        ArrayList<User> users = userMapper.selectLikeCheckedUser("%"+nickname+"%",page,limit);

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
    @GetMapping("/disposeRegisterApply/{flea_id}/{instruct}/{email}")
    public Boolean disposeRegisterApply(@PathVariable("flea_id") String flea_id
            ,@PathVariable("instruct") String instruct
            ,@PathVariable("email") String email){
        System.out.println("fleaid为："+flea_id+" 指令："+instruct+"email为："+email);
        if (instruct.equals("1")){

            sendEmailMessage.sendMessage(email,"您已通过审核，可以登录跳蚤了");
            return userMapper.updateUserStatus(flea_id);
        }else{
            sendEmailMessage.sendMessage(email,"你的条件不符合跳蚤申请要求");

            return userMapper.deleteUser(flea_id);
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
    @GetMapping("/updateUserStatus/{order}/{flea_id}/{email}/{credit}")
    public boolean updateUserStatus(@PathVariable("order") String order
            , @PathVariable("flea_id") String flea_id
            , @PathVariable("email") String email
            , @PathVariable("credit") String credit){
        System.out.println("order:"+order+"flea_id:"+flea_id);

        if (order.equals("ban")){
            sendEmailMessage.sendMessage(email,"你已被管理员永久封禁，信誉积分被扣除"+credit+"分，正在交易的订单已被取消，所有上架物品已被强制下架，如有驳回可找管理员商议");
            //下架所有正在发布的商品

            if(goodsMapper.updateGoodsFreeze(flea_id,3,0,1)){
                ArrayList<String> goodsList = goodsMapper.selectGoodsByUserId(flea_id,3);
                if (goodsList.size()>0){
                    for(String goodsId:goodsList){
                        esDataDao.updateGoodsStatus("goods",goodsId,"freezing");
                    }
                    //强制删除所有正在交易的订单
                    if(orderMapper.deleteOrder(flea_id)&userMapper.updateUserCredit(flea_id,credit)){
                        return userMapper.updateBanstatus(1,flea_id);
                    }else{
                        return false;
                    }
                }else
                    return false;
            }else
                return false;
        }else {
            sendEmailMessage.sendMessage(email,"你已解封，一定要遵守跳蚤APP用户使用守则");
            //商品解除冻结
            goodsMapper.updateGoodsUnFreeze(flea_id,0,3);
            ArrayList<String> goodsList = goodsMapper.selectGoodsByUserId(flea_id,0);
            for(String goodsId:goodsList){
                esDataDao.updateGoodsStatus("goods",goodsId,"living");
            }
            return userMapper.updateBanstatus(0,flea_id);
        }

    }

    /**
     * 暂时封禁用户
     * @param flea_id
     * @param day
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/temporaryBanUser/{flea_id}/{day}/{email}/{credit}")
    public Boolean temporaryBanUser(@PathVariable("flea_id") String flea_id
            ,@PathVariable("day") Integer day
            ,@PathVariable("email") String email
            , @PathVariable("credit") String credit){
        System.out.println("flea_id为："+flea_id+"  day:"+day+" credit为"+credit);
        sendEmailMessage.sendMessage(email,"你已被封禁"+day+"天，信誉积分被扣除"+credit+"分，正在交易的订单已被取消，所有上架物品已被强制下架");
        //所有正在发布的商品
        goodsMapper.updateGoodsFreeze(flea_id,3,0,1);
        userMapper.updateUserCredit(flea_id,credit);
        //强制删除所有正在交易的订单
        orderMapper.deleteOrder(flea_id);
        return userService.temporaryBanUser(flea_id,day);
    }
}
