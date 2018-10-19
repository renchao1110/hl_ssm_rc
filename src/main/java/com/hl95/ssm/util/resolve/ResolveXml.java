package com.hl95.ssm.util.resolve;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 解析xml文件
 * @author: renchao
 * @create: 2018-10-12 13:11
 **/
@Component
public class ResolveXml {
    private static final String MSG="msg";
    private static final String XML="xml";

    public static List<Map> getSaveParams(List<Map<String,String>> list) throws DocumentException, UnsupportedEncodingException {
        List<Map> l = new ArrayList<>();
        for (Map<String,String> map:list){
            Map<String,Object> temp = new HashMap<>();
            String msg = map.get(MSG);
            String xml = map.get(XML);
            Map<String, String> resolveXml = resolveXml(xml);
            List<Map<String, String>> resolveMsg = resolveMsg(msg, resolveXml);
            temp.put("paremXml",resolveXml);
            temp.put("paremMsg",resolveMsg);
            l.add(temp);
        }
        return l;
    }


    public static Map<String,String> resolveXml(String xml) throws UnsupportedEncodingException, DocumentException {
        Map<String,String> xmlMap = new HashMap<>();
        SAXReader reader = new SAXReader();
        Document document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element e:elements){
            String name = e.getName();
            String text = e.getTextTrim();
            xmlMap.put(name,text);
        }
        xmlMap.put("state","1");
        return xmlMap;
    }

    public static List<Map<String,String>> resolveMsg(String msg,Map<String,String> map){
        Map<String,String> result = new HashMap<>();
        List<Map<String,String>> list = new ArrayList<>();

        if (msg.length()!=0){
            String[] split = msg.split("\\|\\$\\|");
            for (String s:split){
                String[] temps = s.split("\\|\\*\\|");
                Map<String,String> msgMap = new HashMap<>();
                if ("0".equals(map.get("SMPRAM"))){
                    if (temps.length==3){
                        msgMap.put("mobile",temps[0]);
                        msgMap.put("orgnumber",temps[1]);
                        msgMap.put("SMContent",temps[2]);
                    }else if (temps.length==2){
                        msgMap.put("mobile",temps[0]);
                        msgMap.put("orgnumber","");
                        msgMap.put("SMContent",temps[1]);
                    }
                }


                if ("1".equals(map.get("SMPRAM"))){
                    if (temps.length==2){
                        msgMap.put("mobile",temps[0]);
                        msgMap.put("orgnumber",temps[1]);
                        msgMap.put("SMContent",map.get("SMContent"));
                    }else if (temps.length==1){
                        msgMap.put("mobile",temps[0]);
                        msgMap.put("orgnumber","");
                        msgMap.put("SMContent",map.get("SMContent"));
                    }
                }

                msgMap.put("UserID",map.get("UserID"));
                msgMap.put("SendID",map.get("SendID"));
                msgMap.put("SendSpeed",map.get("SendSpeed"));
                msgMap.put("RequestTime",map.get("RequestTime"));
                msgMap.put("ScheduleTime",map.get("ScheduleTime"));
                msgMap.put("ExpireTime",map.get("ExpireTime"));
                msgMap.put("state","1");
                msgMap.put("reason","");
                list.add(msgMap);
            }

        }
        return list;
    }
}
