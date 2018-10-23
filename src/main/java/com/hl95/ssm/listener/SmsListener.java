package com.hl95.ssm.listener;

import com.hl95.ssm.task.ReadTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: hl_ssm_rc
 * @description: 保存message Task 以及保存 待发message
 * @author: renchao
 * @create: 2018-10-16 09:45
 **/
@Service
public class SmsListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ReadTask readTask;
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent()==null){
            executorService.scheduleWithFixedDelay(readTask,10000,20000, TimeUnit.MILLISECONDS);
        }
    }
}
