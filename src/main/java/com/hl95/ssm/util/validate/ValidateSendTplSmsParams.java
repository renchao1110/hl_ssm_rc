package com.hl95.ssm.util.validate;

import com.hl95.ssm.dao.MsgTempletMapper;
import com.hl95.ssm.dao.SendTplSmsResultMapper;
import com.hl95.ssm.dao.SendTplsmsMapper;
import com.hl95.ssm.entity.MsgTemplet;
import com.hl95.ssm.util.StringUtils;
import com.hl95.ssm.util.enums.Msg_Tpl_Enums;
import com.hl95.ssm.util.enums.SendTplSmsEnums;
import com.hl95.ssm.util.resolve.ResolveTplParam;
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
 * @create: 2018-09-21 09:40
  **/
@Component
public class ValidateSendTplSmsParams {
    @Autowired
    protected  MsgTempletMapper msgTempletMapper;
    private static final Pattern p = Pattern.compile("[0-9]{1,}");
    public Map<String,Object> validateSendTplSmsParams(Map<String,Object> params){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,Object> result = new HashMap<String,Object>(16);
        //企业ID
        String sn = StringUtils.toString(params.get("sn"));
        //用户密码
        String pwd  = StringUtils.toString(params.get("pwd"));
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
        if(tpl_content==null||StringUtils.isBlank(tpl_content)) {
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_12.getKey(),SendTplSmsEnums.Reason_12.getValue());
            return result;
        }
        if(tpl_id==null||StringUtils.isBlank(tpl_id)){
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_13.getKey(),SendTplSmsEnums.Reason_13.getValue());
            return result;
        }else {
            MsgTemplet msgTemplet = msgTempletMapper.selectByPrimaryKey(tpl_id);
            if (msgTemplet==null){
                result.put(SendTplSmsEnums.Status_04.getKey(), SendTplSmsEnums.Status_04.getValue());
                result.put(SendTplSmsEnums.Reason_04.getKey(),SendTplSmsEnums.Reason_04.getValue());
                return result;
            }
            if ("FALL".equals(msgTemplet.getState())){
                result.put(SendTplSmsEnums.State_01.getKey(), SendTplSmsEnums.State_01.getValue());
                result.put(SendTplSmsEnums.Opinion_01.getKey(),SendTplSmsEnums.Opinion_01.getValue());
                return result;
            }
            if ("CHECK".equals(msgTemplet.getState())){
                result.put(SendTplSmsEnums.State_02.getKey(), SendTplSmsEnums.State_02.getValue());
                result.put(SendTplSmsEnums.Opinion_02.getKey(),SendTplSmsEnums.Opinion_02.getValue());
                return result;
            }
            /*String[] temps = msgTemplet.getTpl_content().split("\\{n\\}");
            for (String sms:temps){
                if (!tpl_content.contains(sms)){
                    result.put(SendTplSmsEnums.Status_09.getKey(), SendTplSmsEnums.Status_09.getValue());
                    result.put(SendTplSmsEnums.Reason_09.getKey(),SendTplSmsEnums.Reason_09.getValue());
                    result.put("tpl_content",msgTemplet.getTpl_content());
                    return result;
                }
            }*/
            tpl_content = ResolveTplParam.getTplSms(msgTemplet.getTpl_content(), tpl_content);
            if ("".equals(tpl_content)){
                result.put(SendTplSmsEnums.Status_09.getKey(), SendTplSmsEnums.Status_09.getValue());
                result.put(SendTplSmsEnums.Reason_09.getKey(),SendTplSmsEnums.Reason_09.getValue());
                result.put("tpl_content",msgTemplet.getTpl_content());
                return result;
            }else {
                params.put("tpl_content",tpl_content);
            }
        }

        if(!Msg_Tpl_ValidateParams.regx(tpl_content)){
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
