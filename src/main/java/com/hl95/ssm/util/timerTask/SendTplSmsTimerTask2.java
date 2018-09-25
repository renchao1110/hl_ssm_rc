package com.hl95.ssm.util.timerTask;

import com.hl95.ssm.entity.SendTplsms;
import com.hl95.ssm.util.send.SendTplSmsPost;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Callable;

/**
 * @program: hl_ssm_rc
 * @description: 用来定时发送模板消息
 * @author: renchao
 * @create: 2018-09-25 13:15
 **/

public class SendTplSmsTimerTask2 implements Callable {

    public SendTplSmsTimerTask2() {
    }

    public SendTplSmsTimerTask2(HttpSession session, List<SendTplsms> list) {
        this.session = session;
        this.list = list;
    }

    private HttpSession session;
    private List<SendTplsms> list;

    @Override
    public Object call() throws Exception {
        return new SendTplSmsPost().sendBatchTplSms(session,list);
    }
}
