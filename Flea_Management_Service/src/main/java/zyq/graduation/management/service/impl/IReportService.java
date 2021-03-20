package zyq.graduation.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyq.graduation.management.mapper.ReportMapper;
import zyq.graduation.management.service.ReportService;

import java.io.Serializable;

/**
 * @author 周杨清
 * @date 2021/3/20 20:01
 */

@Service
public class IReportService implements ReportService {

    @Autowired
    ReportMapper reportMapper;

    @Override
    public Boolean disposeReport(String type, String reportId, String content) {
        if(type.equals("1")){
            return reportMapper.receiveReport(reportId,content);
        }else{
            return reportMapper.refuseReport(reportId,content);
        }
    }
}
