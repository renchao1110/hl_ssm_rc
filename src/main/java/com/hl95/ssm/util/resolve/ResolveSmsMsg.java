package com.hl95.ssm.util.resolve;

import com.hl95.ssm.entity.SendTplsms;
import com.hl95.ssm.util.StringUtils;

import java.util.*;

/**
 * @program: hl_ssm_rc
 * @description: 解析并封装完整的sms信息
 * @author: renchao
 * @create: 2018-09-21 13:16
 **/
public class ResolveSmsMsg {
    public static List<SendTplsms> resolveSms(Map<String,Object> params){
        List<SendTplsms> list = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(128);
        Map<List<SendTplsms>,Map<String,String>> map = new HashMap<>(16);
        //电话号码
        String mobile  = StringUtils.toString(params.get("mobile"));
        //模板ID
        String tpl_id  = StringUtils.toString(params.get("tpl_id"));
        //短信内容
        String tpl_content = StringUtils.toString(params.get("tpl_content"));
        //非必填，用户扩展码
        String ext  = StringUtils.toString(params.get("ext"));
        //定时发送时间
        String stime = StringUtils.toString(params.get("stime"));
        //唯一标识
        String rrid = StringUtils.toString(params.get("rrid"));
        rrid = (rrid==null||"".equals(rrid)) ? UUID.randomUUID().toString().replace("-",""):rrid;
        if (mobile.contains(",")){
            String[] mobiles = mobile.split(",");
            for (String phion:mobiles){
                SendTplsms s = new SendTplsms();
                rrid = UUID.randomUUID().toString().replace("-","");
                s.setRrid(rrid);
                s.setTpl_id(tpl_id);
                s.setTpl_content(tpl_content);
                s.setStime(stime);
                s.setStatus("0");
                s.setReason("成功");
                s.setMobile(phion);
                s.setExt(ext);
                list.add(s);
            }
        }else {
            SendTplsms ss = new SendTplsms();
            ss.setRrid(rrid);
            ss.setTpl_id(tpl_id);
            ss.setTpl_content(tpl_content);
            ss.setStime(stime);
            ss.setStatus("0");
            ss.setReason("成功");
            ss.setMobile(mobile);
            ss.setExt(ext);
            list.add(ss);
        }
        return list;
    }
}
