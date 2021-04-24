package zyq.graduation.management.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zyq.graduation.management.service.AdminLoginRegisterService;

import java.io.File;
import java.io.IOException;

/**
 * @author 周杨清
 * @date 2021/3/30 16:44
 */
@Service
public class IAdminLoginRegisterService implements AdminLoginRegisterService {

    private static String url="http://localhost:8091/test_images/";

    @Override
    public String upload(MultipartFile file, String type) {
        String url2 = url;
        if (file.isEmpty()) {
            System.out.println("上传失败，请选择文件");
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        String filePath = "E:\\test_images\\"+type+"\\";
        url2=url2+type+"/"+fileName;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            //LOGGER.info("上传成功");
            return url2;
        } catch (IOException e) {
            //LOGGER.error(e.toString(), e);
        }
        return "上传失败！";
    }
}
