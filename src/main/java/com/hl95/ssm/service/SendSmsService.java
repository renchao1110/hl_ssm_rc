package com.hl95.ssm.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 普通短信下发接口
 * @author: renchao
 * @create: 2018-09-26 10:40
 **/
public interface SendSmsService {
    Map<String, Object> sendSms(HttpServletRequest request);
}

