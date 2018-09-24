package com.hl95.ssm.service.impl;

import com.hl95.ssm.dao.AddressMapper;
import com.hl95.ssm.dao.MsgTempletMapper;
import com.hl95.ssm.dao.SendTplsmsMapper;
import com.hl95.ssm.entity.SendTplsms;
import com.hl95.ssm.service.SendTplSmsService;
import com.hl95.ssm.util.RemoteHostUtil;
import com.hl95.ssm.util.enums.SendTplSmsEnums;
import com.hl95.ssm.util.resolve.ParamsResolve;
import com.hl95.ssm.util.resolve.ResolveSmsMsg;
import com.hl95.ssm.util.validate.ValidateSendTplSmsParams;
import com.hl95.ssm.util.validate.ValidateUserAndPwd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 发送模板消息的业务处理层
 * @author: renchao
 * @create: 2018-09-20 13:15
 **/
@Service
public class SendTplSmsServiceImpl implements SendTplSmsService {
    private static final String SUCCESS = "0";
    private static final String STATUS = "status";
    @Autowired
    private ValidateSendTplSmsParams validateSendTplSmsParams;
    @Autowired
    private ValidateUserAndPwd validateUserAndPwd;
    private PlatformTransactionManager ptm;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private MsgTempletMapper msgTempletMapper;
    @Autowired
    private SendTplsmsMapper sendTplsmsMapper;
    @Override
    public Map<String, Object> sendTplSms(HttpServletRequest request) {
        List<Object> l = new ArrayList<>();
        Map<String,Object> result = new HashMap<String,Object>(16);
        //1.获取请求参数。
        Map<String, Object> params = ParamsResolve.getParams(request);
        //2.参数校验
        Map<String, Object> resultMap = validateSendTplSmsParams.validateSendTplSmsParams(params);
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
        if (!validateUserAndPwd.validateUserAndPwd(params)){
            result.put(SendTplSmsEnums.Status_01.getKey(), SendTplSmsEnums.Status_01.getValue());
            result.put(SendTplSmsEnums.Reason_01.getKey(),SendTplSmsEnums.Reason_01.getValue());
            return result;
        }
        //5.解析并封装要保存的参数
        List<SendTplsms> sendTplsms = ResolveSmsMsg.resolveSms(params);
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //TransactionStatus satus = ptm.getTransaction(definition);
        try {
            sendTplsmsMapper.saveBatch(sendTplsms);
            for (SendTplsms s:sendTplsms){
                Map<String,Object> tempMap = new HashMap<String,Object>(16);
                tempMap.put("rrid",s.getRrid());
                tempMap.put("status",s.getStatus());
                tempMap.put("reason",s.getReason());
                l.add(tempMap);
            }
            result.put("result",l);
            return result;
        }catch (Exception e){
            //ptm.rollback(satus);
            for (SendTplsms s:sendTplsms){
                result.put("rrid",s.getRrid());
                result.put("status","-1");
                result.put("reason","提交失败");
            }
            e.printStackTrace();
            //return result;
        }
        //ptm.commit(satus);
        return result;
    }
}
