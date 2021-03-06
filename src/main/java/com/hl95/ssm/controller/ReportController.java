package com.hl95.ssm.controller;

import com.hl95.ssm.service.StateReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 获取短信下发的状态报告
 * @author: renchao
 * @create: 2018-09-26 15:33
 **/
@Controller
public class ReportController {
    @Autowired
    private StateReportService stateReportService;

    /**
     * 获取状态报告接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/getreport",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getReport(HttpServletRequest request){
        return stateReportService.getStateReport(request);
    }


    /**
     * 接收并保存状态报告
     * @param request
     * @return
     */
    @RequestMapping(value = "/receivereport",method = RequestMethod.POST)
    @ResponseBody
    public String receiveReport(HttpServletRequest request){
        return stateReportService.saveReport(request);
    }
}
