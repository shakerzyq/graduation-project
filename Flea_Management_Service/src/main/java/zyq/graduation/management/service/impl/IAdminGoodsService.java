package zyq.graduation.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyq.graduation.management.mapper.AdminGoodsMapper;
import zyq.graduation.management.mapper.AdminReportMapper;
import zyq.graduation.management.mapper.AdminUserMapper;
import zyq.graduation.management.pojo.AdminGeneral;
import zyq.graduation.management.pojo.Goods;
import zyq.graduation.management.pojo.GoodsReport;
import zyq.graduation.management.pojo.User;
import zyq.graduation.management.service.AdminGoodsService;
import zyq.graduation.management.util.SendEmailMessage;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/5/6 16:29
 */
@Service
public class IAdminGoodsService implements AdminGoodsService {

    @Autowired
    AdminGoodsMapper adminGoodsMapper;

    @Autowired
    AdminReportMapper adminReportMapper;

    @Autowired
    AdminUserMapper adminUserMapper;

    @Autowired
    SendEmailMessage sendEmailMessage;

    @Override
    public ArrayList<GoodsReport> getGoodsReports(Integer page, Integer limit, String adminAccount) {
        //获取管理员所在的院校
        String adminAddress = adminGoodsMapper.selectAddressOfAdmin(adminAccount);
        System.out.println("adminAddres为："+adminAddress);
        //查询对应院校的举报单
        return adminGoodsMapper.selectGoodsReport(adminAddress);
    }

    @Override
    public ArrayList<GoodsReport> getGoodsAccomplishReports(int page, Integer limit,String userId,String status,String adminAccount) {
        //查询admin信息
        String result = userId==null||userId.length()==0?"0":"1";
        result+=(status==null||status.length()==0)?"2":status;
        String address =adminGoodsMapper.selectAddressOfAdmin(adminAccount);
        switch (result){
            case "02":
                return adminGoodsMapper.selectAccomplishGoodsReport(page,limit,address);
            case "00":
            case "01":
                return adminGoodsMapper.selectAccomplishReportStatus(page,limit,status,address);
            case "12":
                return adminGoodsMapper.selectAccomplishReportById(page,limit,userId,address);
            case "10":
            case "11":
                return adminGoodsMapper.selectAccomplishReportByIdByStatus(page,limit,userId,status,address);
            default:
                return null;
        }
    }

    @Override
    public Boolean disposeGoodsReport(String type, String reportId, String content, String complain_userid, String complained_userid, String goods_id, String adminAccount) {
        User user1 = adminUserMapper.selectUser(complained_userid);
        User user2 = adminUserMapper.selectUser(complain_userid);
        Goods goods = adminGoodsMapper.selectGoodsByGoodsId(goods_id);
        if(type.equals("1")){
            //查询被举报人email
            String content1 = "你发布的商品("+goods.getProduct_title()+")已被认定为违规(详细请看举报单)"+"已作出信誉积分扣除或封禁惩罚"+"管理员留言："+content;
            sendEmailMessage.sendMessage(user1.getEmail(),content1);
            //查询举报人email
            String content2 = "你举报的商品("+goods.getProduct_title()+")已被认定为违规(详细请看举报单)"+"该用户("+user1.getNickname()+")已被进行信誉积分扣除或封禁惩罚，如果对方没能执行要求请与管理员联系"+"管理员留言："+content;
            sendEmailMessage.sendMessage(user2.getEmail(),content2);
            return adminGoodsMapper.receiveGoodsReport(reportId,content,adminAccount);
        }else{
            String content1 = "你发布的商品("+goods.getProduct_title()+")没有违规，管理员留言："+content;
            sendEmailMessage.sendMessage(user1.getEmail(),content1);
            //查询举报人email
            String content2 = "你举报的商品("+goods.getProduct_title()+")没有违规，如有问题可以管理员联系，管理员留言："+content;
            sendEmailMessage.sendMessage(user2.getEmail(),content2);
            return adminGoodsMapper.refuseGoodsReport(reportId,content,adminAccount);
        }
    }
}
