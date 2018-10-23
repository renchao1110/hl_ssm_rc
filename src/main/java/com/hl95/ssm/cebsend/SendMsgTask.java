package com.hl95.ssm.cebsend;

import com.hl95.ssm.dao.SmsConductMapper;
import com.hl95.ssm.dao.SmsTaskMapper;
import com.hl95.ssm.entity.SmsTask;
import com.hl95.ssm.task.SendSmsTask;
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
 * @description: 获取要发送的 主表 任务
 * @author: renchao
 * @create: 2018-10-17 15:08
 **/
@Component
public class SendMsgTask {
    @Autowired
    private SmsTaskMapper smsTaskMapper;
    @Autowired
    private SmsConductMapper smsConductMapper;
    @Autowired
    private SendSmsTask sendSmsTask;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");


    public List<Map<String,Object>> getConductSms() throws ParseException {
        List<SmsTask> tasks = smsTaskMapper.getTasks();
        List<Map<String, Object>> msgs = new ArrayList<>();
        List<Integer> errorIds = new ArrayList<>();
        List<Integer> okIds = new ArrayList<>();
        for (SmsTask task:tasks){
            if (SDF.parse(task.getExpiretime()).compareTo(new Date())>=0){
                msgs = smsConductMapper.getMsgsByTaskId(task.getUserid(), task.getSendid());
                okIds.add(task.getId());
            }else {
                errorIds.add(task.getId());
            }
            if (errorIds.size()>0){
                smsTaskMapper.updateErrorStateById(errorIds);
            }
            if (okIds.size()>0){
                smsTaskMapper.updateStateById(okIds);
            }
        }
        return msgs;
    }
}
