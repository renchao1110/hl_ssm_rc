package com.hl95.ssm.util.enums;

public enum Msg_Tpl_Enums implements BaseEnums<String>{
    Msg_Tpl_Enums_01("reason","提交成功"),
    Msg_Tpl_Enums_02("reason","企业ID不能为空"),
    Msg_Tpl_Enums_03("reason","密码不能为空"),
    Msg_Tpl_Enums_04("reason","密码错误"),
    Msg_Tpl_Enums_05("reason","模板内容超过500字限制"),
    Msg_Tpl_Enums_06("reason","模板内容不能为空"),
    Msg_Tpl_Enums_07("reason","用户不存在"),
    Msg_Tpl_Enums_08("status","0"),
    Msg_Tpl_Enums_09("reason","tpl_id不能为空"),
    Msg_Tpl_Enums_10("reason","error!tpl_id不存在，模板信息更新失败"),
    Msg_Tpl_Enums_11("reason","error!tpl_id不存在，模板信息删除失败");

    Msg_Tpl_Enums(String key, String value) {
        this.key = key;
        this.value = value;
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

    public static void main(String[] args) {
        System.out.println(Msg_Tpl_Enums_02.getKey());
        System.out.println(Msg_Tpl_Enums_02.getValue());
    }
}
