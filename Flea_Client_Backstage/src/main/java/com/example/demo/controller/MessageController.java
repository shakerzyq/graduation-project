package com.example.demo.controller;

import com.example.demo.mapper.MessageMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.CommentShow;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/17 23:36
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    MessageMapper messageMapper;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getGoodsComments/{flea_id}/{table}")
    public ArrayList<Comment> getGoodsComments(@PathVariable("flea_id") String flea_id,@PathVariable("table") String table){
        return messageService.s_getGoodsComment(flea_id,table);
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @PutMapping("/updateMessageViewStatus/{Id}/{table}")
    public Boolean updateMessageViewStatus(@PathVariable("Id") String Id,@PathVariable("table") String table){
//        messageService.s_updateMessageViewStatus(Id,table)
        if (table.equals("comment")){
            return messageMapper.updateViewStatusA(Id);
        }else{
            return messageMapper.updateViewStatusB(Id);
        }

    }

}
