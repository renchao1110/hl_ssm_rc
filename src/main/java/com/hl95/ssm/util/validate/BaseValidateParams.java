package com.hl95.ssm.util.validate;

import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 校验前台参数的基类
 * @author: rc
 * @create: 2018-09-18 17:26
 **/
public interface BaseValidateParams {
    /**
     *验证模板报备接口参数
     * @param params 表单参数
     * @return 响应消息
     */
    Map<String,Object> validateAddParams(Map<String, Object> params);

    /**
     *验证模板获取接口参数
     * @param params 表单参数
     * @return 响应消息
     */
    Map<String,Object> validateGetParams(Map<String, Object> params);

    /**
     *验证模板更改接口参数
     * @param params 表单参数
     * @return 响应消息
     */
    Map<String,Object> validateUpdateParams(Map<String, Object> params);

    /**
     *验证模板删除接口参数
     * @param params 表单参数
     * @return 响应消息
     */
    Map<String,Object> validateDeleteParams(Map<String, Object> params);
}
