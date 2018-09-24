package com.hl95.ssm.controller;

import com.hl95.ssm.service.SendTplSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 发送模板信息的接口
 * @author: renchao
 * @create: 2018-09-20 13:10
 **/
@Controller
public class SendTplSmsController {
    @Autowired
    private SendTplSmsService sendTplSmsService;

    @RequestMapping(value = "/sendtplsms",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> sendTplSms(HttpServletRequest request){
        return sendTplSmsService.sendTplSms(request);
    }
}
