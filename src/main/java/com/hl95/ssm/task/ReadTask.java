package com.hl95.ssm.task;

import com.hl95.ssm.dao.SmsConductMapper;
import com.hl95.ssm.dao.SmsTaskMapper;
import com.hl95.ssm.util.read.ReadSNDFile;
import com.hl95.ssm.util.resolve.ResolveXml;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: ${description}
 * @author: renchao
 * @create: 2018-10-16 10:05
 **/
@Component
public class ReadTask implements Runnable {
    @Autowired
    private SmsTaskMapper smsTaskMapper;
    @Autowired
    private SmsConductMapper smsConductMapper;
    @Autowired
    private ReadSNDFile readSNDFile;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Override
    public void run() {

        System.out.println("########4秒执行一次##########"+sdf.format(new Date()));
        List<Map> saveParams = null;
        try {
            List<Map<String, String>> list = readSNDFile.readSNDFile();
            saveParams = ResolveXml.getSaveParams(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map map:saveParams){
            Object paremXml = map.get("paremXml");
            Object paremMsg = map.get("paremMsg");
            if (paremXml instanceof Map){
                try {
                    smsTaskMapper.saveSmsTask((Map<String, String>) paremXml);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
            if (paremMsg instanceof List){
                try {
                    smsConductMapper.saveBatchMsg((List<Map<String, Object>>) paremMsg);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }

    }
}
