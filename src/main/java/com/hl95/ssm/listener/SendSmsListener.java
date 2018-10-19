package com.hl95.ssm.listener;

import com.hl95.ssm.cebsend.SendMsgTask;
import com.hl95.ssm.task.SendSmsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: hl_ssm_rc
 * @description: 短信下发监听器
 * @author: renchao
 * @create: 2018-10-16 16:38
 **/
@Service
public class SendSmsListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private SendSmsTask sendMsgTask;
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent()==null){
            //executorService.scheduleWithFixedDelay(sendMsgTask,7000,1000, TimeUnit.MILLISECONDS);
        }
    }
}
