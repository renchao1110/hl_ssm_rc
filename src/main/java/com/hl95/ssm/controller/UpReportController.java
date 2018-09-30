package com.hl95.ssm.controller;

import com.hl95.ssm.service.UpReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 用来处理短信上行控制器
 * @author: renchao
 * @create: 2018-09-27 13:39
 **/
@Controller
public class UpReportController {
    @Autowired
    private UpReportService upReportService;
    @RequestMapping(value = "/receiveUpReport",method = RequestMethod.POST)
    @ResponseBody
    public String receiveReport(HttpServletRequest request){
        return upReportService.saveUpReport(request);
    }

    @RequestMapping(value = "/getUpreport",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getReport(HttpServletRequest request){
        return upReportService.getUpReport(request);
    }
}
