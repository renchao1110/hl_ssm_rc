package com.hl95.ssm.service.impl;

import com.hl95.ssm.dao.AddressMapper;
import com.hl95.ssm.dao.MsgTempletMapper;
import com.hl95.ssm.dao.SendTplsmsMapper;
import com.hl95.ssm.dao.UserMapper;
import com.hl95.ssm.entity.SendTplsms;
import com.hl95.ssm.entity.User;
import com.hl95.ssm.service.SendTplSmsService;
import com.hl95.ssm.util.RemoteHostUtil;
import com.hl95.ssm.util.enums.SendTplSmsEnums;
import com.hl95.ssm.util.resolve.ParamsResolve;
import com.hl95.ssm.util.resolve.ResolveSmsMsg;
import com.hl95.ssm.util.send.SendTplSmsPost;
import com.hl95.ssm.util.timerTask.SendTplSmsTimerTask;
import com.hl95.ssm.util.timerTask.SendTplSmsTimerTask2;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private PlatformTransactionManager ptm;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private MsgTempletMapper msgTempletMapper;
    @Autowired
    private SendTplsmsMapper sendTplsmsMapper;
    @Autowired
    private SendTplSmsPost sendTplSmsPost;
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
        if (!validateUserAndPwd.validateUserAndPwd(params,request.getSession())){
            result.put(SendTplSmsEnums.Status_01.getKey(), SendTplSmsEnums.Status_01.getValue());
            result.put(SendTplSmsEnums.Reason_01.getKey(),SendTplSmsEnums.Reason_01.getValue());
            return result;
        }
        //5.解析并封装要保存的参数
        List<SendTplsms> sendTplsms = ResolveSmsMsg.resolveSms(params);
        /*DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus satus = ptm.getTransaction(definition);*/
        //if (sendTplsms.size()>1){
            try {
                sendTplsmsMapper.saveBatch(sendTplsms);
                String stime = "";
                //ptm.commit(satus);
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
                        //ScheduledExecutorService scheduExec = Executors.newScheduledThreadPool(2);
                        SendTplSmsTimerTask task = new SendTplSmsTimerTask(session,sendTplsms,sendTplsmsMapper);
                        //Timer timer = new Timer();
                        new Timer().schedule(task,date);
                        //SendTplSmsTimerTask2 smsTimerTask2 = new SendTplSmsTimerTask2(session,sendTplsms);
                        //ScheduledFuture schedule = scheduExec.schedule(smsTimerTask2, date.getTime(), TimeUnit.MILLISECONDS);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else {
                    Map<String, String> map = sendTplSmsPost.sendBatchTplSms(request.getSession(true), sendTplsms);
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
                    sendTplsmsMapper.updateByOK(rridsOk);
                }
                if (rridsError.size()!=0){
                    sendTplsmsMapper.updateByError(rridsError);
                }
                result.put("result",l);
                //ptm.commit(satus);
                return result;
            }catch (Exception e){
                //ptm.rollback(satus);
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
        /*}else{
            SendTplsms tplsms = sendTplsms.get(0);
            sendTplsmsMapper.saveOne(tplsms);
            String stime = sendTplsms.get(0).getStime();
            if (stime!=null&&!"".equals(stime)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date = sdf.parse(stime);
                    HttpSession session = request.getSession();
                    SendTplSmsTimerTask task = new SendTplSmsTimerTask(session,sendTplsms);
                    //Timer timer = new Timer();
                    new Timer().schedule(task,date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else {
                sendTplSmsPost.sendTplSms(request.getSession(true),sendTplsms.get(0));
            }
        }*/
        return result;


    }
}
