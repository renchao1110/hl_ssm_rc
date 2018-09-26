package com.hl95.ssm.util.validate;

import com.hl95.ssm.util.StringUtils;
import com.hl95.ssm.util.enums.Msg_Tpl_Enums;
import com.hl95.ssm.util.enums.SendTplSmsEnums;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: hl_ssm_rc
 * @description: 模板报备接口参数验证
 * @author: rc
 * @create: 2018-09-18 17:38
 **/
@Component("msg_Tpl_ValidateParams")
public class Msg_Tpl_ValidateParams implements BaseValidateParams{
    private static final int TPL_MSG_MAX = 500;
    @Override
    public Map<String, Object> validateAddParams(Map<String, Object> params) {
        Map<String,Object> result = new HashMap<String,Object>(16);
        //企业ID
        String sn = StringUtils.toString(params.get("sn"));
        //用户密码
        String pwd = StringUtils.toString(params.get("pwd"));
        //短信内容tplContent
        String tpl_content  = StringUtils.toString(params.get("tpl_content"));
        if(sn==null||StringUtils.isBlank(sn)){
            result.put(SendTplSmsEnums.Status_03.getKey(),SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_10.getKey(),SendTplSmsEnums.Reason_10.getValue());
            return result;
        }
        if(pwd==null||StringUtils.isBlank(pwd)){
            result.put(SendTplSmsEnums.Status_03.getKey(),SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_11.getKey(),SendTplSmsEnums.Reason_11.getValue());
            return result;
        }
        if(tpl_content==null||StringUtils.isBlank(tpl_content)) {
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_12.getKey(),SendTplSmsEnums.Reason_12.getValue());
            return result;
        }
        if(!Msg_Tpl_ValidateParams.regx(tpl_content)){
            result.put(SendTplSmsEnums.Status_02.getKey(), SendTplSmsEnums.Status_02.getValue());
            result.put(SendTplSmsEnums.Reason_02.getKey(),SendTplSmsEnums.Reason_02.getValue());
            return result;
        }
        if(tpl_content.length()>TPL_MSG_MAX){
            result.put(Msg_Tpl_Enums.Msg_Tpl_Enums_05.getKey(),Msg_Tpl_Enums.Msg_Tpl_Enums_05.getValue());
            return result;
        }
        result.put(Msg_Tpl_Enums.Msg_Tpl_Enums_01.getKey(),Msg_Tpl_Enums.Msg_Tpl_Enums_01.getValue());
        return result;
    }

    @Override
    public Map<String, Object> validateGetParams(Map<String, Object> params) {
        Map<String,Object> result = new HashMap<String,Object>(16);
        //企业ID
        String sn = StringUtils.toString(params.get("sn"));
        //用户密码
        String pwd = StringUtils.toString(params.get("pwd"));
        //短信模板ID
        String tpl_id  = StringUtils.toString(params.get("tpl_id"));
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
        result.put(Msg_Tpl_Enums.Msg_Tpl_Enums_01.getKey(),Msg_Tpl_Enums.Msg_Tpl_Enums_01.getValue());
        return result;
    }

    @Override
    public Map<String, Object> validateUpdateParams(Map<String, Object> params) {
        Map<String,Object> result = new HashMap<String,Object>(16);
        //企业ID
        String sn = StringUtils.toString(params.get("sn"));
        //用户密码
        String pwd = StringUtils.toString(params.get("pwd"));
        //短信内容tpl_content
        String tpl_content  = StringUtils.toString(params.get("tpl_content"));
        //短信模板ID
        String tpl_id  = StringUtils.toString(params.get("tpl_id"));
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
        if(tpl_id==null||StringUtils.isBlank(tpl_id)){
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_13.getKey(),SendTplSmsEnums.Reason_13.getValue());
            return result;
        }
        if(tpl_content==null||StringUtils.isBlank(tpl_content)) {
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_12.getKey(),SendTplSmsEnums.Reason_12.getValue());
            return result;
        }
        if(!Msg_Tpl_ValidateParams.regx(tpl_content)){
            result.put(SendTplSmsEnums.Status_02.getKey(), SendTplSmsEnums.Status_02.getValue());
            result.put(SendTplSmsEnums.Reason_02.getKey(),SendTplSmsEnums.Reason_02.getValue());
            return result;
        }
        if(tpl_content.length()>TPL_MSG_MAX){
            result.put(Msg_Tpl_Enums.Msg_Tpl_Enums_05.getKey(),Msg_Tpl_Enums.Msg_Tpl_Enums_05.getValue());
            return result;
        }
        result.put(Msg_Tpl_Enums.Msg_Tpl_Enums_01.getKey(),Msg_Tpl_Enums.Msg_Tpl_Enums_01.getValue());
        return result;
    }

    @Override
    public Map<String, Object> validateDeleteParams(Map<String, Object> params) {
        Map<String,Object> result = new HashMap<String,Object>(16);
        //企业ID
        String sn = StringUtils.toString(params.get("sn"));
        //用户密码
        String pwd = StringUtils.toString(params.get("pwd"));
        //短信模板ID
        String tpl_id  = StringUtils.toString(params.get("tpl_id"));
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
        if(tpl_id==null||StringUtils.isBlank(tpl_id)){
            result.put(SendTplSmsEnums.Status_03.getKey(), SendTplSmsEnums.Status_03.getValue());
            result.put(SendTplSmsEnums.Reason_13.getKey(),SendTplSmsEnums.Reason_13.getValue());
            return result;
        }
        result.put(Msg_Tpl_Enums.Msg_Tpl_Enums_01.getKey(),Msg_Tpl_Enums.Msg_Tpl_Enums_01.getValue());
        return result;
    }


    public static boolean regx(String tpl_content){
        String regExp = "^【\\S{1,}】{1}";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(tpl_content);
        if (matcher.find()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        boolean regx = regx("【哈哈哈啥】");
        System.out.println(regx);
    }
}
