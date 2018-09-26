package com.hl95.ssm.controller;

import com.hl95.ssm.service.SendSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 发送普通短信
 * @author: renchao
 * @create: 2018-09-26 10:38
 **/
@Controller
public class SendSmsController {
    @Autowired
    private SendSmsService sendSmsService;
    @RequestMapping(value = "/sendsms",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> sendTplSms(HttpServletRequest request){
        return sendSmsService.sendSms(request);
    }
}
