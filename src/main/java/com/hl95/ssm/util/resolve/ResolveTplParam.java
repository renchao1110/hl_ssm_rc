package com.hl95.ssm.util.resolve;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: hl_ssm_rc
 * @description: 解析模板的变量重新组装模板信息内容
 * @author: renchao
 * @create: 2018-09-28 12:58
 **/
public class ResolveTplParam {

    private static final Pattern pattern= Pattern.compile("\\{n\\}");
    public static String getTplSms(String tplSms,String tplParams){
        StringBuffer b = new StringBuffer();
        int tplCount = 0;
        int paramCount = 0;
        String group = "";
        Matcher matcher = pattern.matcher(tplSms);
        String[] tplSmss = pattern.split(tplSms);
        String[] params = tplParams.split(",");
        tplCount = tplSmss.length-1;
        paramCount = params.length;
        if (tplCount==paramCount){
            for (int i=0;i<tplSmss.length;i++){
                String temp1 = tplSmss[i];
                String temp2 = "";
                if (i<paramCount){
                    temp2 = params[i];
                }
                b.append(temp1).append(temp2);
            }
            return b.toString();
        }
        return "";
    }

    public static void main(String[] args) {
        String tplSms = "【鸿联九五】您好{n}，您的验证码是：{n}---{n}。";
        String tplParams = "任超,123,111";
        String sms = getTplSms(tplSms, tplParams);
        System.out.println(sms);
    }
}
