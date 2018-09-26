package com.hl95.ssm.util.timerTask;

import com.hl95.ssm.dao.SendTplSmsResultMapper;
import com.hl95.ssm.dao.SendTplsmsMapper;
import com.hl95.ssm.entity.SendTplsms;
import com.hl95.ssm.util.send.SendTplSmsPost;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * @program: hl_ssm_rc
 * @description: 用来定时发送模板消息
 * @author: renchao
 * @create: 2018-09-25 13:15
 **/

public class SendTplSmsTimerTask extends TimerTask {


    public SendTplSmsTimerTask() {
    }

    public SendTplSmsTimerTask(HttpSession session, List<SendTplsms> list,SendTplsmsMapper sendTplsmsMapper,SendTplSmsResultMapper sendTplSmsResultMapper) {
        this.session = session;
        this.list = list;
        this.sendTplsmsMapper = sendTplsmsMapper;
        this.sendTplSmsResultMapper = sendTplSmsResultMapper;
    }

    private HttpSession session;
    private List<SendTplsms> list;
    private SendTplsmsMapper sendTplsmsMapper;
    private SendTplSmsResultMapper sendTplSmsResultMapper;
    @Override
    public void run() {
        List<String> rridsOk = new ArrayList<>();
        List<String> rridsError = new ArrayList<>();
        Map<String, String> result = new SendTplSmsPost().sendBatchTplSms(session, list);
        for (Map.Entry<String,String> entry:result.entrySet()){
            String rrid  = entry.getKey();
            String status  = entry.getValue();
            if ("00".equals(status)){
                rridsOk.add(rrid);
            }else {
                rridsError.add(rrid);
            }
        }
        if (rridsOk.size()!=0){
            sendTplSmsResultMapper.saveBatch(rridsOk);
            sendTplsmsMapper.deleteByrrids(rridsOk);
        }
        if (rridsError.size()!=0){
            sendTplsmsMapper.updateByError(rridsError);
        }
    }

}
