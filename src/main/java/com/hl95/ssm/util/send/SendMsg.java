package com.hl95.ssm.util.send;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

public class SendMsg {
	private static final String URL = "http://219.239.7.151:8099/returl/info!getResp.dhtml";
	public static String sendPost(Map<String, Object> params){
		CloseableHttpClient client = HttpClients.createDefault();
		BufferedReader in = null;
		String body = "";
		CloseableHttpResponse response = null;
		try {
			
			HttpPost httpPost = new HttpPost(URL);
			//装填参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			
			if(params!=null){
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					String key = entry.getKey();
					String value = (String)entry.getValue();
					nvps.add(new BasicNameValuePair(key, value));
				}
			}
			//设置参数到请求对象中
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=GBK");
			httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			response = client.execute(httpPost);
			//获取结果实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				//按指定编码转换结果实体为String类型
				body = EntityUtils.toString(entity);
			}
			EntityUtils.consume(entity);
			return body;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			//释放链接
			if(response!=null){
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
}
