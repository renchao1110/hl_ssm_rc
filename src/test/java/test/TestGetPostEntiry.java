package test;

import com.hl95.ssm.entity.SendTplsms;
import com.hl95.ssm.entity.User;
import com.hl95.ssm.util.send.SendTplSmsPost;
import org.apache.http.NameValuePair;

import java.util.List;

/**
 * @program: hl_ssm_rc
 * @description: ${description}
 * @author: renchao
 * @create: 2018-09-25 10:14
 **/
public class TestGetPostEntiry {

    public static void main(String[] args) {
        User user = new User();
        SendTplsms sms = new SendTplsms();
        user.setId(111);
        user.setPwd("111pwd");
        user.setSn("111sn222");
        user.setUsername("111username");
        sms.setRrid("1111rrid");
        sms.setExt("ext111");
        sms.setMobile("12134543213MObile");
        sms.setReason("reason00");
        sms.setStatus("status-0");
        sms.setStime("2018-12-33");
        sms.setTpl_content("短信内容");
        sms.setTpl_id("tpl_id-111");
        sms.setState("state-00");

        List<NameValuePair> postEntity = SendTplSmsPost.getPostEntity(user, sms);
        System.out.println(postEntity);
    }
}
