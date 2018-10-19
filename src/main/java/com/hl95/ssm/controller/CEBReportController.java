package com.hl95.ssm.controller;

import com.hl95.ssm.service.CEBStateReportService;
import com.hl95.ssm.service.impl.CEBStateReportServiceImpl;
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
 **/
@Controller
public class CEBReportController {
    @Autowired
    private CEBStateReportService cebStateReportService;
    /**
     * 接收并保存状态报告
     * @param request
     * @return
     */
    @RequestMapping(value = "/receiveCEBreport",method = RequestMethod.POST)
    @ResponseBody
    public String receiveReport(HttpServletRequest request){
        return cebStateReportService.saveReport(request);
    }
}
