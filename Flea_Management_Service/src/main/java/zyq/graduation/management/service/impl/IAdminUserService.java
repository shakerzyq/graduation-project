package zyq.graduation.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyq.graduation.management.mapper.AdminUserMapper;
import zyq.graduation.management.service.AdminUserService;

/**
 * @author 周杨清
 * @date 2021/3/23 18:42
 */
@Service
public class IAdminUserService implements AdminUserService {

    @Autowired
    AdminUserMapper adminUserMapper;

    /**
     * 暂时封禁用户
     * @param flea_id
     * @param day
     * @return
     */
    @Override
    public Boolean temporaryBanUser(String flea_id, Integer day) {
//        86400000
        long deadline = System.currentTimeMillis()+day*86400000;//计算截止日期
        //插入截止日期
        return adminUserMapper.updateDeadline(deadline,flea_id);
    }
}
