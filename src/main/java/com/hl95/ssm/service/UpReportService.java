package com.hl95.ssm.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 短信上行业务接口
 * @author: renchao
 * @create: 2018-09-27 13:41
 **/
public interface UpReportService {
    int saveUpReport(HttpServletRequest request);
    /**
     * 获取上行信息的接口
     * @param request
     * @return
     */
    Map<String, Object> getUpReport(HttpServletRequest request);
}
