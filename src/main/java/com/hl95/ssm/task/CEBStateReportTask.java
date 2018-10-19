package com.hl95.ssm.task;

import com.hl95.ssm.dao.SmsConductMapper;
import com.hl95.ssm.dao.SmsTaskMapper;
import com.hl95.ssm.dao.StateReportMapper;
import com.hl95.ssm.service.CEBStateReportService;
import com.hl95.ssm.service.impl.CEBStateReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 扫描状态报告线程
 * @author: renchao
 * @create: 2018-10-18 13:59
 **/
@Component
public class CEBStateReportTask implements Runnable{
    @Autowired
    private CEBStateReportService cebStateReportService;
    @Override
    public void run() {
        List<Map<String, Object>> reportIds = cebStateReportService.getReportIds();
        cebStateReportService.getReports(reportIds);
    }
}
