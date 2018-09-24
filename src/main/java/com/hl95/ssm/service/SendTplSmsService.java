package com.hl95.ssm.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 发送模板消息的接口
 * @author: renchao
 * @create: 2018-09-20 13:13
 **/
public interface SendTplSmsService {
    Map<String,Object> sendTplSms(HttpServletRequest request);
}
