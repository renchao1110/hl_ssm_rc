package com.hl95.ssm.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 获取状态报告的接口，以及储存状态报告
 * @author: renchao
 * @create: 2018-09-26 15:38
 **/
public interface StateReportService {
    /**
     * 获取状态报告的接口
     * @param request
     * @return
     */
    Map<String, Object> getStateReport(HttpServletRequest request);

    /**
     * 接收企信通并储存状态报告
     * @param request
     * @return
     */
    String saveReport(HttpServletRequest request);
}
