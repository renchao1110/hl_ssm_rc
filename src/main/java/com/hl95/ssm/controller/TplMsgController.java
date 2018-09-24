package com.hl95.ssm.controller;

import com.hl95.ssm.service.TplMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 短信模板报备接口
 * @author: renchao
 * @create: 2018-09-19 11:11
 **/
@Controller
public class TplMsgController {
    @Autowired
    private TplMsgService tplAddService;
    @RequestMapping(value="/tplAdd",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> tplAdd(HttpServletRequest request){
        Map<String, Object> result = tplAddService.tpladd(request);
        return result;
    }

    @RequestMapping(value="/tplGet",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> tplget(HttpServletRequest request){
        Map<String, Object> result = tplAddService.gettpl(request);
        return result;
    }


    @RequestMapping(value="/tplUpdate",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> tplUpdate(HttpServletRequest request){
        Map<String, Object> result = tplAddService.updatetpl(request);
        return result;
    }

    @RequestMapping(value="/tplDel",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> tplDel(HttpServletRequest request){
        Map<String, Object> result = tplAddService.tplDel(request);
        return result;
    }


}
