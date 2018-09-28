package com.hl95.ssm.service.impl;

import com.hl95.ssm.dao.AddressMapper;
import com.hl95.ssm.dao.SendTplSmsResultMapper;
import com.hl95.ssm.dao.StateReportMapper;
import com.hl95.ssm.service.StateReportService;
import com.hl95.ssm.util.RemoteHostUtil;
import com.hl95.ssm.util.enums.SendTplSmsEnums;
import com.hl95.ssm.util.resolve.ParamsResolve;
import com.hl95.ssm.util.resolve.ResolveResult;
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
    private static final String REASON = "成功";
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ValidateGetReportParams validateSendTplSmsParams;
    @Autowired
    private ValidateUserAndPwd validateUserAndPwd;
    @Autowired
    private StateReportMapper stateReportMapper;

    @Autowired
    private SendTplSmsResultMapper sendTplSmsResultMapper;

    /**
     * 客户获取状态报告的接口
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> getStateReport(HttpServletRequest request) {
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

        List<Map<String, Object>> reports = stateReportMapper.getReports();
        if (reports.size()==0){
            Map<String,Object> temp = new HashMap<>();
            temp.put("status","-99");
            temp.put("reason","当前没有可用的状态报告");
            reports.add(temp);
            result.put("result",reports);
            return result;
        }
        stateReportMapper.updateReports(reports);
        List<Map<String, Object>> list = ResolveResult.resolveResultMap(reports);
        result.put("result",list);
        return result;
    }

    /**
     * 接收状态报告并根据状态报告FLinkID更新短信下发状态
     * @param request
     * @return
     */
    @Override
    public int saveReport(HttpServletRequest request) {
        //1.获取请求参数。
        Map<String, Object> params = ParamsResolve.getParams(request);
        //首次保存添加默认值
        params.put("state","0");
        params.put("reason",REASON);
        //转化时间
        String FSubmitTime = (String) params.get("FSubmitTime");
        if (!FSubmitTime.contains("-")){
            String s = ResolveResult.resolvestrToDate(FSubmitTime);
            params.put("FSubmitTime",s);

        }
        //保存状态报告
        int i = stateReportMapper.saveReport(params);
        //更新短信下发状态
        String rrid = (String) params.get("FLinkID");


        if (i==1&&rrid!=null&&!"".equals(rrid)){
            Map<String,Object> map = new HashMap<>();
            map.put("rrid",rrid);
            map.put("state",params.get("FReportCode"));
            map.put("stime",params.get("FSubmitTime"));
            if ("DELIVRD".equals((String)params.get("FReportCode"))||"0".equals((String)params.get("FReportCode"))){
                map.put("reason","短信下发成功");
            }else {
                map.put("reason","短信下发失败");
            }
            sendTplSmsResultMapper.updateByRrid(map);
        }
        return i;
    }
}
