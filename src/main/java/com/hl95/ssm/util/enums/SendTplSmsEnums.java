package com.hl95.ssm.util.enums;

/**
 * @program: hl_ssm_rc
 * @description: 封装一些响应消息
 * @author: renchao
 * @create: 2018-09-20 13:17
 **/
public enum SendTplSmsEnums implements BaseEnums<String>{

    State_00("state","SUCCESS"),
    Opinion_00("opinion","审核通过"),
    State_01("state","FALL"),
    Opinion_01("opinion","审核不通过"),
    State_02("state","CHECK"),
    Opinion_02("opinion","待审核"),
    Status_00("status","0"),
    Status_01("status","-1"),
    Status_02("status","-2"),
    Status_03("status","-3"),
    Status_04("status","-4"),
    Status_05("status","-5"),
    Status_06("status","-6"),
    Status_07("status","-7"),
    Status_08("status","-8"),
    Status_09("status","-9"),
    Status_99("status","99"),
    Reason_00("reason","成功"),
    Reason_01("reason","账号密码错误"),
    Reason_02("reason","模板中无签名"),
    Reason_03("reason","参数不合法"),
    Reason_04("reason","模板id不存在"),
    Reason_05("reason","审核未通过:模板没有带签名"),
    Reason_06("reason","stime格式不正确，yyyy-MM-dd HH:ss:mm"),
    Reason_07("reason","stime小于当前系统时间"),
    Reason_08("reason","IP鉴权失败,请联系管理员！"),
    Reason_09("reason","短信内容与模板内容不匹配"),
    Reason_10("reason","sn不能为空"),
    Reason_11("reason","pwd不能为空"),
    Reason_12("reason","tpl_content不能为空"),
    Reason_13("reason","tpl_id不能为空"),
    Reason_14("reason","mobile不能为空"),
    Reason_15("reason","mobile格式不正确"),
    Reason_16("reason","ext格式不正确"),
    Reason_17("reason","content不能为空"),
    Reason_99("reason","其他错误")
    ;

    SendTplSmsEnums(String key, String value){
        this.key=key;
        this.value=value;
    }

    private String key;
    private String value;
    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
