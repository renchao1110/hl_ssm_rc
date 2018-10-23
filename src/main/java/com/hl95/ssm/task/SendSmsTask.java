package com.hl95.ssm.task;

import com.hl95.ssm.cebsend.SendMsgTask;
import com.hl95.ssm.dao.SmsConductMapper;
import com.hl95.ssm.dao.SmsTaskMapper;
import com.hl95.ssm.entity.SmsTask;
import com.hl95.ssm.util.send.SendCEBMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: hl_ssm_rc
 * @description: 根据任务表 获取要 发送的短信
 * @author: renchao
 * @create: 2018-10-16 16:42
 **/
@Component
public class SendSmsTask implements Runnable{
    @Autowired
    private SendMsgTask sendMsgTask;
    @Autowired
    private SmsConductMapper smsConductMapper;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    @Override
    public void run() {
        System.out.println("#############################开始读取数据库发送短信#####################################");
        List<Integer> ids = new ArrayList<>();
        List<Integer> errorids = new ArrayList<>();
        String s  = "";
        try {
            List<Map<String, Object>> conductSms = sendMsgTask.getConductSms();
            for (Map<String, Object> sms:conductSms){
                sms.put("username","pjs");
                sms.put("password","pjs123456");
                sms.put("epid","109765");
                String scheduletime = (String)sms.get("scheduletime");
                String expiretime = (String)sms.get("expiretime");
                long delay = SDF.parse(scheduletime).getTime()-new Date().getTime()+1000;
                long expire = SDF.parse(expiretime).getTime()-new Date().getTime();
                if (delay>0&&expire>=0){
                    executorService.schedule(new Runnable() {
                        @Override
                        public void run() {
                           String s = SendCEBMsg.sendPost(sms);
                            if ("00".equals(s)){
                                ids.add((Integer) sms.get("linkid"));
                            }else {
                                errorids.add((Integer) sms.get("linkid"));
                            }
                            if (ids.size()>0){
                                smsConductMapper.updateByIds(ids);
                            }
                            if (errorids.size()>0){
                                smsConductMapper.updateByErrorIds(errorids);
                            }
                        }
                    },delay, TimeUnit.MILLISECONDS);
                }else {
                    if (expire>0){
                        s = SendCEBMsg.sendPost(sms);
                        if ("00".equals(s)){
                            ids.add((Integer) sms.get("linkid"));
                        }else {
                            errorids.add((Integer) sms.get("linkid"));
                        }
                    }else {
                        errorids.add((Integer) sms.get("linkid"));
                    }
                }
            }

            if (ids.size()>0){
                smsConductMapper.updateByIds(ids);
            }
            if (errorids.size()>0){
                smsConductMapper.updateByErrorIds(errorids);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
