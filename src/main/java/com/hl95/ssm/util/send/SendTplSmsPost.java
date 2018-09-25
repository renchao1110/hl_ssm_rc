package com.hl95.ssm.util.send;

import com.hl95.ssm.dao.UserMapper;
import com.hl95.ssm.entity.SendTplsms;
import com.hl95.ssm.entity.User;
import com.sun.tools.javac.util.ArrayUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 用来发送模板消息
 * @author: renchao
 * @create: 2018-09-25 09:09
 **/
@Component
public class SendTplSmsPost {
    @Autowired
    private UserMapper userMapper;
    private static final String sendURL = "http://q.hl95.com:8061";
    public String sendTplSms(HttpSession session, SendTplsms tplsms){
        User user = (User) session.getAttribute("user");
        if (user==null){
            user = userMapper.findUserBySnid("109765");
        }
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(sendURL);
        List<NameValuePair> postEntity = getPostEntity(user, tplsms);
        String body = "";
        CloseableHttpResponse httpResponse = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(postEntity,Charset.forName("GBK")));
            post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=GBK");
            post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            httpResponse = client.execute(post);

            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(httpEntity);
            }
            EntityUtils.consume(httpEntity);
            return body;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放链接
            if(httpResponse!=null){
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return body;
    }


    public Map<String,String> sendBatchTplSms(HttpSession session, List<SendTplsms> tplsmss){
        String body = "-1";
        Map<String,String> result = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user==null){
            user = userMapper.findUserBySnid("109765");
        }
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(sendURL);
        post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=GBK");
        post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        for (SendTplsms tplsms :tplsmss){
            List<NameValuePair> postEntity = getPostEntity(user, tplsms);
            CloseableHttpResponse httpResponse = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(postEntity,Charset.forName("GBK")));
                httpResponse = client.execute(post);
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    //按指定编码转换结果实体为String类型
                    body = EntityUtils.toString(httpEntity);
                }
                EntityUtils.consume(httpEntity);
                result.put(tplsms.getRrid(),body);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                //释放链接
                if(httpResponse!=null){
                    try {
                        httpResponse.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //return result;
        }
        return result;
    }


    public static List<NameValuePair> getPostEntity(User user,SendTplsms tplsms){
        List<NameValuePair> pairList = new ArrayList<>();
        Field[] userFields = user.getClass().getDeclaredFields();
        Field[] smsFields = tplsms.getClass().getDeclaredFields();
        String key = "";
        Object value = "";
        try {
            for (Field field : userFields) {
                field.setAccessible(true);
                key = field.getName();
                if (key.equals("sn")){
                    key = "epid";
                }
                value = field.get(user);
                if (value!=null){
                    pairList.add(new BasicNameValuePair(key, value.toString()));
                }

            }
            for (Field field : smsFields) {
                field.setAccessible(true);
                key = field.getName();
                if (key.equals("mobile")){
                    key = "phone";
                }
                if (key.equals("tpl_content")){
                    key = "message";
                }
                if (key.equals("ext")){
                    key = "subcode";
                }
                if (key.equals("rrid")){
                    key = "linkid";
                }
                value = field.get(tplsms);
                if (value!=null){
                    pairList.add(new BasicNameValuePair(key, value.toString()));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return pairList;
    }


}
