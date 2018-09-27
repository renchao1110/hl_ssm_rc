package com.hl95.ssm.util.validate;

import com.hl95.ssm.dao.MsgTempletMapper;
import com.hl95.ssm.util.StringUtils;
import com.hl95.ssm.util.enums.SendTplSmsEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @program: hl_ssm_rc
 * @description: 校验模板短信下发参数
 * @author: renchao
 * @create: 2018-09-26 09:40
  **/
@Component
public class ValidateGetReportParams {
    public Map<String,Object> validateGetReportParams(Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>(16);
        //企业ID
        String sn = StringUtils.toString(params.get("sn"));
        //用户密码
        String pwd  = StringUtils.toString(params.get("pwd"));
        if(sn==null||StringUtils.isBlank(sn)){
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_10.getKey(),SendTplSmsEnums.Reason_10.getValue()+":sn不能为空");
            return result;
        }
        if(pwd==null||StringUtils.isBlank(pwd)){
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_11.getKey(),SendTplSmsEnums.Reason_11.getValue()+":pwd不能为空");
            return result;
        }
        result.put(SendTplSmsEnums.Status_00.getKey(),SendTplSmsEnums.Status_00.getValue());
        return result;
    }
}
