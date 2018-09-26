package com.hl95.ssm.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 获取状态报告的接口
 * @author: renchao
 * @create: 2018-09-26 15:38
 **/
public interface StateReportService {
    Map<String, Object> getStateReport(HttpServletRequest request);
}
