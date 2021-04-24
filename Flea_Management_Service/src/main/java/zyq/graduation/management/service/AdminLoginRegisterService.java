package zyq.graduation.management.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 周杨清
 * @date 2021/3/30 16:44
 */
public interface AdminLoginRegisterService {
    public String upload(MultipartFile file, String type);
}
