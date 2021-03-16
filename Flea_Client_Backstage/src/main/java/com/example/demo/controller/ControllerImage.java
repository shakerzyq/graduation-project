package com.example.demo.controller;


import com.example.demo.mapper.SwipperMapper;
import com.example.demo.pojo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "用户数据接口")

public class ControllerImage {

    @Autowired
    SwipperMapper swipperMapper;

    //@Autowired


    /**
     * 获取轮播图
     * @return
     *//*
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping("/swipper")
    @ApiOperation(value ="查询用户", notes = "根据 id 查询用户")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户 id", required = true)
    public List<ImageSwipper> swipperImage(){
        System.out.println("Hello World");
        List<ImageSwipper> images = new ArrayList<ImageSwipper>();
        images=swipperMapper.getImageSwp();
        return images;
    }*/

    /**
     * 获取推荐商品
     * @param pagenum
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping("/hotgoods/{pagenum}")
    public List<Recommend> hotGoods(@PathVariable("pagenum") String pagenum){
        System.out.println(pagenum);
         return swipperMapper.getGoods(((Integer.parseInt(pagenum)-1)*8),8);
    }

   /* *//**
     * 获取图片类型
     * @return
     *//*
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping("/pictures")
    public List<PictureType> PictureTypes(){
        return swipperMapper.getPicType();
    }

    *//**
     * 获取图片类型对应的图片
     *//*
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping("/pictures/{id}")
    public List<Images> getImages(@PathVariable("id") String imgId){
        return swipperMapper.getImages(Integer.parseInt(imgId));
    }

    *//**
     * 获取news资讯
     *//*
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping("/news")
    public List<News> getnews(){
        return swipperMapper.getNews();
    }*/

    /**
     * 获取json数据
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping("/schools")
//    四川，重庆，西藏，新疆，青海，甘肃，宁夏，陕西，山西，内蒙古，黑龙江，辽宁,吉林，天津，山东，河北
//    湖北，湖南，江苏，安徽，浙江，上海，福建，江西，广西，广东，海南，贵州云南
    public void getCollege( @RequestBody ArrayList<College> colleges){
        System.out.println("收到消息");
        for(College college:colleges){
            System.out.println(college.getName());
            swipperMapper.InsertCollege(college.getName(),college.getAcademy_type(),college.getSchool_type(),college.getAddress(),college.getRank()
            ,college.getPhone(),college.getContact_address(),college.getAscription(),college.getSite());
        }
    }



    /**
     * 实现简单的文件上传递
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file){
        System.out.println("hello world");
        if (file.isEmpty()) {
            System.out.println("上传失败，请选择文件");
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        String filePath = "E:\\毕业设计\\软件内容\\upload_test\\";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            //LOGGER.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
            //LOGGER.error(e.toString(), e);
        }
        return "上传失败！";
    }

//    /**
//     * 生成md5码
//     */
//    @CrossOrigin(origins = "*",maxAge = 3600)
//    @GetMapping("/make_md5/{account}/{pwd}")
//    public String makeMD5(@PathVariable("account") String account,@PathVariable("pwd") String pwd) throws Exception {
//        ApiClient_HmacMD5 apiClient_hmacMD5 = new ApiClient_HmacMD5();
//        return apiClient_hmacMD5.makeMD5(account,pwd);
//    }
}
