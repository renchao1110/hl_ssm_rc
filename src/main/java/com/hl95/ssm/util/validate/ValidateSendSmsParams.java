package com.hl95.ssm.util.validate;

import com.hl95.ssm.dao.MsgTempletMapper;
import com.hl95.ssm.entity.MsgTemplet;
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
public class ValidateSendSmsParams {
    @Autowired
    protected  MsgTempletMapper msgTempletMapper;
    private static final Pattern p = Pattern.compile("[0-9]{1,}");
    public Map<String,Object> validateSendSmsParams(Map<String,Object> params){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,Object> result = new HashMap<String,Object>(16);
        //企业ID
        String sn = StringUtils.toString(params.get("sn"));
        //用户密码
        String pwd  = StringUtils.toString(params.get("pwd"));
        //电话号码
        String mobile  = StringUtils.toString(params.get("mobile"));
        //短信内容
        String content = StringUtils.toString(params.get("content"));
        //非必填，用户扩展码
        String ext  = StringUtils.toString(params.get("ext"));
        //非必填，定时发送时间
        String stime = StringUtils.toString(params.get("stime"));
        //非必填，唯一标识
        String rrid = StringUtils.toString(params.get("rrid"));
        if(sn==null||StringUtils.isBlank(sn)){
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_10.getKey(),SendTplSmsEnums.Reason_10.getValue());
            return result;
        }
        if(pwd==null||StringUtils.isBlank(pwd)){
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_11.getKey(),SendTplSmsEnums.Reason_11.getValue());
            return result;
        }
        if(mobile==null||StringUtils.isBlank(mobile)){
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_14.getKey(),SendTplSmsEnums.Reason_14.getValue());
            return result;
        }

        //验证手机号格式
        if (mobile.contains(",")){
            String[] strs = mobile.split(",");
            for (String temp:strs) {
                if(!p.matcher((CharSequence)temp).matches()){
                    result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
                    result.put(SendTplSmsEnums.Reason_15.getKey(),SendTplSmsEnums.Reason_15.getValue());
                    if(temp.length()!=11) {
                        result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
                        result.put(SendTplSmsEnums.Reason_15.getKey(),SendTplSmsEnums.Reason_15.getValue());
                        return result;
                    }
                    return result;
                }
            }
        }else{
            if(!p.matcher((CharSequence)mobile).matches()){
                result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
                result.put(SendTplSmsEnums.Reason_15.getKey(),SendTplSmsEnums.Reason_15.getValue());
                if(mobile.length()!=11) {
                    result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
                    result.put(SendTplSmsEnums.Reason_15.getKey(),SendTplSmsEnums.Reason_15.getValue());
                    return result;
                }
                return result;
            }
        }
        if(content==null||StringUtils.isBlank(content)) {
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_17.getKey(),SendTplSmsEnums.Reason_17.getValue());
            return result;
        }
        if(!Msg_Tpl_ValidateParams.regx(content)){
            result.put(SendTplSmsEnums.Status_02.getKey(), SendTplSmsEnums.Status_02.getValue());
            result.put(SendTplSmsEnums.Reason_02.getKey(),SendTplSmsEnums.Reason_02.getValue());
            return result;
        }
        if(ext!=null&&!"".equals(ext)){
            if(!p.matcher((CharSequence)ext).matches()||ext.length()>5){
                result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
                result.put(SendTplSmsEnums.Reason_16.getKey(),SendTplSmsEnums.Reason_16.getValue());
                return result;
            }
        }
        if (stime!=null&&!"".equals(stime)){
            try {
                Date date = sdf.parse(stime);
                long time = date.getTime();
                long nowTime = System.currentTimeMillis();
                if (time<nowTime){
                    result.put(SendTplSmsEnums.Status_07.getKey(), SendTplSmsEnums.Status_07.getValue());
                    result.put(SendTplSmsEnums.Reason_07.getKey(),SendTplSmsEnums.Reason_07.getValue());
                    return result;
                }
            }catch (ParseException e){
                result.put(SendTplSmsEnums.Status_06.getKey(), SendTplSmsEnums.Status_06.getValue());
                result.put(SendTplSmsEnums.Reason_06.getKey(),SendTplSmsEnums.Reason_06.getValue());
                e.printStackTrace();
                return result;
            }

        }
        result.put(SendTplSmsEnums.Status_00.getKey(),SendTplSmsEnums.Status_00.getValue());
        return result;
    }
}
