package com.hl95.ssm.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 获取状态报告的接口，以及储存状态报告
 * @author: renchao
 **/
public interface CEBStateReportService {

    /**
     * 接收企信通并储存状态报告
     * @param request
     * @return
     */
    String saveReport(HttpServletRequest request);

    List<Map<String, Object>> getReportIds();

    void getReports(List<Map<String, Object>> list);

    void writeFile(String fileName,List<Map<String, String>> list);
}
