package com.hl95.ssm.service.impl;

import com.hl95.ssm.dao.AddressMapper;
import com.hl95.ssm.dao.SendTplSmsResultMapper;
import com.hl95.ssm.dao.SendTplsmsMapper;
import com.hl95.ssm.entity.SendTplsms;
import com.hl95.ssm.service.SendSmsService;
import com.hl95.ssm.util.RemoteHostUtil;
import com.hl95.ssm.util.enums.SendTplSmsEnums;
import com.hl95.ssm.util.resolve.ParamsResolve;
import com.hl95.ssm.util.resolve.ResolveSms;
import com.hl95.ssm.util.resolve.ResolveSmsMsg;
import com.hl95.ssm.util.send.SendTplSmsPost;
import com.hl95.ssm.util.timerTask.SendTplSmsTimerTask;
import com.hl95.ssm.util.validate.ValidateSendSmsParams;
import com.hl95.ssm.util.validate.ValidateSendTplSmsParams;
import com.hl95.ssm.util.validate.ValidateUserAndPwd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: hl_ssm_rc
 * @description: 普通短信下发业务实现
 * @author: renchao
 * @create: 2018-09-26 10:42
 **/
@Service
public class SendSmsServiceImpl implements SendSmsService {
    private static final String SUCCESS = "0";
    private static final String STATUS = "status";
    @Autowired
    private ValidateSendSmsParams validateSendSmsParams;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ValidateUserAndPwd validateUserAndPwd;
    @Autowired
    private SendTplsmsMapper sendTplsmsMapper;
    @Autowired
    private SendTplSmsResultMapper sendTplSmsResultMapper;
    @Autowired
    private SendTplSmsPost sendTplSmsPost;
    @Override
    public Map<String, Object> sendSms(HttpServletRequest request) {
        List<Object> l = new ArrayList<>();
        Map<String,Object> result = new HashMap<String,Object>(16);
        //1.获取请求参数。
        Map<String, Object> params = ParamsResolve.getParams(request);
        //2.参数校验
        Map<String, Object> resultMap = validateSendSmsParams.validateSendSmsParams(params);
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
        //5.解析并封装要保存的参数
        List<SendTplsms> sendTplsms = ResolveSms.resolveSms(params);
        try {
            //保存要发送的消息
            sendTplsmsMapper.saveBatch(sendTplsms);
            String stime = "";
            List<String> rridsOk = new ArrayList<>();
            List<String> rridsError = new ArrayList<>();
            for (SendTplsms s:sendTplsms){
                Map<String,Object> tempMap = new HashMap<String,Object>(16);
                tempMap.put("rrid",s.getRrid());
                tempMap.put("status",s.getStatus());
                tempMap.put("reason",s.getReason());
                l.add(tempMap);
                stime = s.getStime();
            }


            if (stime!=null&&!"".equals(stime)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date = sdf.parse(stime);
                    HttpSession session = request.getSession();
                    //定时执行短信发送任务
                    SendTplSmsTimerTask task = new SendTplSmsTimerTask(session,sendTplsms,sendTplsmsMapper,sendTplSmsResultMapper);
                    new ScheduledThreadPoolExecutor(1).schedule(task,date.getTime()-System.currentTimeMillis(), TimeUnit.MILLISECONDS);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else {
                Map<String, String> map = sendTplSmsPost.sendBatchTplSms(request.getSession(true), sendTplsms);
                //根据返回信息更新数据库信息
                for (Map.Entry<String,String> entry:map.entrySet()){
                    String rrid  = entry.getKey();
                    String status  = entry.getValue();
                    if ("00".equals(status)){
                        rridsOk.add(rrid);
                    }else {
                        rridsError.add(rrid);
                    }
                }
            }
            if (rridsOk.size()!=0){
                sendTplSmsResultMapper.saveBatch(rridsOk);
                sendTplsmsMapper.deleteByrrids(rridsOk);

            }
            if (rridsError.size()!=0){
                sendTplsmsMapper.updateByError(rridsError);
            }
            result.put("result",l);
            return result;
        }catch (Exception e){
            for (SendTplsms s:sendTplsms){
                Map<String,Object> tempMap = new HashMap<String,Object>(16);
                tempMap.put("rrid",s.getRrid());
                tempMap.put("status","-1");
                tempMap.put("reason","提交失败");
                l.add(tempMap);
            }
            result.put("result",l);
            e.printStackTrace();
        }
        return result;
    }
}
