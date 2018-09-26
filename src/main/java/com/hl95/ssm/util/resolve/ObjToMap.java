package com.hl95.ssm.util.resolve;

import com.hl95.ssm.entity.MsgTemplet;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @program: hl_ssm_rc
 * @description: 将对象转换为map
 * @author: renchao
 * @create: 2018-09-26 17:50
 **/
public class ObjToMap {

    public static Map<String,Object> objToMap(Object object){
        Map<String,Object> result = new HashMap<>(16);
        Field[] fields = object.getClass().getDeclaredFields();
        String key = "";
        Object value = "";
        for (Field field: fields){
            field.setAccessible(true);
            key = field.getName();
            try {
            if ("tpl_id".equals(key)){
                value = field.get(object);
                result.put(key,value);
                continue;
            }
            if ("tpl_content".equals(key)){
                value = field.get(object);
                result.put(key,value);
                continue;
            }
            if ("state".equals(key)){
                value = field.get(object);
                result.put("status",value);
                continue;
            }
            if ("opinion".equals(key)){
                value = field.get(object);
                result.put("reason",value);
                continue;
            }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static Map<String,Object>  listToMap(List<MsgTemplet> list){
        Map<String,Object>  result = new HashMap<>();
        List<Map<String,Object> > l = new ArrayList<>();
        for (Object obj:list){
            Map<String,Object> temp = new HashMap<>(16);
            Field[] fields = obj.getClass().getDeclaredFields();
            String key = "";
            Object value = "";
            for (Field field: fields){
                field.setAccessible(true);
                key = field.getName();
                try {
                    if ("tpl_id".equals(key)){
                        value = field.get(obj);
                        temp.put(key,value);
                        continue;
                    }
                    if ("tpl_content".equals(key)){
                        value = field.get(obj);
                        temp.put(key,value);
                        continue;
                    }
                    if ("state".equals(key)){
                        value = field.get(obj);
                        temp.put("status",value);
                        continue;
                    }
                    if ("opinion".equals(key)){
                        value = field.get(obj);
                        temp.put("reason",value);
                        continue;
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            l.add(temp);
        }
        result.put("result",l);
        return result;
    }


    public static Map<String,Object>  resolveMap(Map<String,Object> map){
        Map<String,Object> result = new HashMap<>(16);
        Map<String,Object> temp = new HashMap<>(16);
        List<Map<String,Object> > l = new ArrayList<>();
        for (Map.Entry<String,Object> entry:map.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            if ("tpl_id".equals(key)){
                temp.put(key,value);
                continue;
            }
            if ("tpl_content".equals(key)){
                temp.put(key,value);
                continue;
            }
            if ("status".equals(key)){
                temp.put("status",value);
                continue;
            }
            if ("opinion".equals(key)){
                temp.put("reason",value);
                continue;
            }
        }
        l.add(temp);
        result.put("result",l);
        return result;
    }

}
