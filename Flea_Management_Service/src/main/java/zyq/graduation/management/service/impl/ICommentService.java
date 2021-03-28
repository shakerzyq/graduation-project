package zyq.graduation.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyq.graduation.management.mapper.CommentMapper;
import zyq.graduation.management.mapper.ReportMapper;
import zyq.graduation.management.pojo.CommentReport;
import zyq.graduation.management.service.CommentService;

import java.util.ArrayList;

/**
 * @author 周杨清
 * @date 2021/3/27 15:59
 */
@Service
public class ICommentService implements CommentService {

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    CommentMapper commentMapper;

    @Override
    public ArrayList<CommentReport> getCommentReport(Integer page, Integer limit, String userId,String type) {
        String result = (userId==null||userId.length()==0)?"0":"1";
        result+=(type==null||type.length()==0)?"2":type;
        switch (result){
            case "00":
            case "01":
                return reportMapper.selectCommentsReport(page,limit);
            case "10":
                return reportMapper.selectCommentsReportComplainedById(page,limit,userId,type);
            case "11":
                return reportMapper.selectCommentsReportComplainById(page,limit,userId,type);
            case "12":
                return reportMapper.selectCommentsReportById(page,limit,userId);


        }
        if (userId==null||userId.length()==0){
            return reportMapper.selectCommentsReport(page,limit);
        }else{
            return reportMapper.selectCommentsReportById(page,limit,userId);
        }
    }

    @Override
    public ArrayList<CommentReport> getAccomplishReports(Integer page,Integer limit,String userId, String status) {
        String result = userId==null||userId.length()==0?"0":"1";
        result+=(status==null||status.length()==0)?"2":status;
        switch (result){
            case "02":
                return commentMapper.selectAccomplishReport(page,limit);
            case "00":
            case "01":
                return commentMapper.selectAccomplishReportStatus(page,limit,status);
            case "12":
                return commentMapper.selectAccomplishReportById(page,limit,userId);
            case "10":
            case "11":
                return commentMapper.selectAccomplishReportByIdByStatus(page,limit,userId,status);
        }
        return null;
    }
}
