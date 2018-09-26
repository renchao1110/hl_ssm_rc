package com.hl95.ssm.service.impl;

import com.hl95.ssm.dao.AddressMapper;
import com.hl95.ssm.service.StateReportService;
import com.hl95.ssm.util.RemoteHostUtil;
import com.hl95.ssm.util.enums.SendTplSmsEnums;
import com.hl95.ssm.util.resolve.ParamsResolve;
import com.hl95.ssm.util.validate.ValidateGetReportParams;
import com.hl95.ssm.util.validate.ValidateUserAndPwd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 获取状态报告的业务实现
 * @author: renchao
 * @create: 2018-09-26 15:39
 **/
@Service
public class StateReportServiceImpl implements StateReportService {
    private static final String SUCCESS = "0";
    private static final String STATUS = "status";
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ValidateGetReportParams validateSendTplSmsParams;
    @Autowired
    private ValidateUserAndPwd validateUserAndPwd;
    @Override
    public Map<String, Object> getStateReport(HttpServletRequest request) {
        List<Object> l = new ArrayList<>();
        Map<String,Object> result = new HashMap<String,Object>(16);
        //1.获取请求参数。
        Map<String, Object> params = ParamsResolve.getParams(request);
        //2.参数校验
        Map<String, Object> resultMap = validateSendTplSmsParams.validateGetReportParams(params);
        if (!SUCCESS.equals(resultMap.get(STATUS))){
            return resultMap;
        }
        //3.ip鉴权
        String host = RemoteHostUtil.getRemoteHost(request);
        int countIp = addressMapper.getCountIp(host);
        if (countIp<=0){
            result.put(SendTplSmsEnums.Status_08.getKey(), SendTplSmsEnums.Status_08.getValue());
            result.put(SendTplSmsEnums.Reason_08.getKey(),SendTplSmsEnums.Reason_08.getValue());
            result.put("ERROR IP",host);
            return result;
        }
        //4.用户校验
        if (!validateUserAndPwd.validateUserAndPwd(params,request.getSession())){
            result.put(SendTplSmsEnums.Status_01.getKey(), SendTplSmsEnums.Status_01.getValue());
            result.put(SendTplSmsEnums.Reason_01.getKey(),SendTplSmsEnums.Reason_01.getValue());
            return result;
        }

        return null;
    }
}
