package com.hl95.ssm.listener;

import com.hl95.ssm.service.impl.CEBStateReportServiceImpl;
import com.hl95.ssm.task.CEBStateReportTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: hl_ssm_rc
 * @description: ${description}
 * @author: renchao
 * @create: 2018-10-18 16:21
 **/
@Service
public class WriterRptFileListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private CEBStateReportTask cebStateReportTask;
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent()==null){
            //executorService.scheduleWithFixedDelay(cebStateReportTask,15000,20000, TimeUnit.MILLISECONDS);
        }
    }
}
