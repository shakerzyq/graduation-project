package zyq.graduation.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyq.graduation.management.mapper.AdminGoodsMapper;
import zyq.graduation.management.mapper.AdminReportMapper;
import zyq.graduation.management.mapper.AdminUserMapper;
import zyq.graduation.management.pojo.Goods;
import zyq.graduation.management.pojo.Report;
import zyq.graduation.management.pojo.User;
import zyq.graduation.management.service.AdminReportService;
import zyq.graduation.management.util.SendEmailMessage;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/20 20:01
 */

@Service
public class IAdminReportService implements AdminReportService {

    @Autowired
    AdminReportMapper adminReportMapper;

    @Autowired
    AdminUserMapper adminUserMapper;

    @Autowired
    AdminGoodsMapper adminGoodsMapper;

    @Autowired
    SendEmailMessage sendEmailMessage;

    /**
     * 处理举报
     * @param type 为：1，通过举报  为 2：拒绝举报
     * @param reportId
     * @param content
     * @return
     */
    @Override
    public Boolean disposeReport(String type, String reportId, String content,String complain_userid,String flea_id,String goods_id,String admin_id) {
        User user1 = adminUserMapper.selectUser(flea_id);
        User user2 = adminUserMapper.selectUser(complain_userid);
        Goods goods = adminGoodsMapper.selectGoodsByGoodsId(goods_id);
        if(type.equals("1")){
            //查询被举报人email
            String content1 = "你发布的商品("+goods.getProduct_title()+")相关的交易订单已被认定为违规(详细请看举报单)"+"已作出信誉积分扣除或封禁惩罚"+"管理员留言："+content;
            sendEmailMessage.sendMessage(user1.getEmail(),content1);
            //查询举报人email
            String content2 = "你举报的商品("+goods.getProduct_title()+")相关的交易订单已被认定为违规(详细请看举报单)"+"该用户("+user1.getNickname()+")已被进行信誉积分扣除或封禁惩罚，如果对方没能执行要求请与管理员联系"+"管理员留言："+content;
            sendEmailMessage.sendMessage(user2.getEmail(),content2);
            return adminReportMapper.receiveReport(reportId,content,admin_id);
        }else{
            String content1 = "你发布的商品("+goods.getProduct_title()+")相关的交易订单没有违规，管理员留言："+content;
            sendEmailMessage.sendMessage(user1.getEmail(),content1);
            //查询举报人email
            String content2 = "你举报的商品("+goods.getProduct_title()+")相关的交易订单没有违规，如有问题可以管理员联系，管理员留言："+content;
            sendEmailMessage.sendMessage(user2.getEmail(),content2);
            return adminReportMapper.refuseReport(reportId,content,admin_id);
        }
    }

    /**
     * 获取未处理举报
     * @param page
     * @param limit
     * @param type 0，买方举报，1：卖方举报，2：任何举报，3：通过用户id查询,4: 通过商品id查询
     * @param flea_id
     * @return
     */
    @Override
    public ArrayList<Report> getPendingReports(Integer page, Integer limit, Integer type, String flea_id
            , String goodsId) {
        System.out.println(page+":"+limit);
        ArrayList<Report> reports=null;
        Integer type2=null;
        String goodsid=null;
        String fleaid=null;
        String result=null;
        if (goodsId==null||goodsId.length()==0){
            goodsid="0";
        }else{
            goodsid="1";
        }

        if(flea_id==null||flea_id.length()==0){
            fleaid="0";
        }else{
            fleaid="1";
        }

        result=type+goodsid+fleaid;
        System.out.println("result为："+result);
        switch (result){
            case "000"://买方举报、没有goodsid和fleaid
                return adminReportMapper.selectReport000_100(page,limit,0+"");
            case "001"://买方举报、通过用户id查询
                return adminReportMapper.selectReport001(page,limit,0+"", flea_id);
            case "010"://买方举报、通过商品id查询
                return adminReportMapper.selectReport010(page,limit,0+"", goodsId);
            case "011"://买方举报、通过用户id查询、商品id查询
                return adminReportMapper.selectReport011(page,limit,0+"",goodsId, flea_id);
            case "100"://卖方举报、没有goodsid和fleaid
                return adminReportMapper.selectReport000_100(page,limit,1+"");
            case "101"://卖方举报、输入fleaid
                return adminReportMapper.selectReport001(page,limit,1+"", flea_id);
            case "110"://卖方举报、输入goodsid
                return adminReportMapper.selectReport010(page,limit,1+"", goodsId);
            case "111"://卖方举报、输入goodsid、输入fleaid
                return adminReportMapper.selectReport011(page,limit,1+"",goodsId, flea_id);
            case "200"://综合查询，没有条件
                return adminReportMapper.selectReport(page,limit);
            case "201"://通过用户id查询
                return adminReportMapper.selectReport201(page,limit,flea_id);
            case "210"://通过商品id查询
                return adminReportMapper.selectReport210(page,limit,goodsId);
            case "211"://通过商品id查询、用户id查询
                return adminReportMapper.selectReport211(page,limit,goodsId, flea_id);
        }
        return reports;
    }

    /**
     * 获取处理完成的举报单
     * @param page
     * @param limit
     * @param reportType
     * @param resultType
     * @return
     */
    @Override
    public ArrayList<Report> getAccomplishReports(Integer page, Integer limit,Integer reportType, Integer resultType,String adminAccount) {

        String type=reportType+""+resultType;
        switch(type){
            case "22"://不分类
                return adminReportMapper.selectAccomplishReport(page,limit);
            case "12":
            case "02":
                return adminReportMapper.selectAccomplishReport1(page,limit,reportType);
            case "21":
            case "20":
                return adminReportMapper.selectAccomplishReport2(page,limit,resultType);
            case "11":
            case "10":
            case "01":
            case "00":
                return adminReportMapper.selectAccomplishReport3(page,limit,reportType,resultType);
            default:
                return null;
        }
    }


}
