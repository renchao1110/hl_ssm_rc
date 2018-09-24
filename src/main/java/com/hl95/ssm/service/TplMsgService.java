package com.hl95.ssm.service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 模板报备接口
 * @author: rc
 * @create: 2018-09-19 11:06
 **/
public interface TplMsgService {
    /**
     * 模板报备接口
     * @param request
     * @return
     */
    Map<String,Object> tpladd(HttpServletRequest request);

    /**
     * 模板获取接口
     * @param request
     * @return 获取的结果
     */
    Map<String,Object> gettpl(HttpServletRequest request);

    /**
     * 模板修改接口
     * @param request
     * @return
     */
    Map<String,Object> updatetpl(HttpServletRequest request);

    /**
     * 模板删除接口
     * @param request
     * @return
     */
    Map<String, Object> tplDel(HttpServletRequest request);
}
