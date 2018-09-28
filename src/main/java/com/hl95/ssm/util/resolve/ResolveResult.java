package com.hl95.ssm.util.resolve;

import java.util.List;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 解析封装返回结果
 * @author: renchao
 * @create: 2018-09-27 16:46
 **/
public class ResolveResult {

    public static List<Map<String, Object>> resolveResultMap(List<Map<String, Object>> list){
        for (Map<String, Object> map : list){
            for (Map.Entry entry:map.entrySet()){
                if (entry.getKey().equals("id")){
                    map.remove("id");
                    break;
                }
            }
        }
        return list;
    }

    /**
     * 将无格式的字符串日期转换为yyyy-MM-dd HH:mm:ss
     * @param str
     * @return
     */
    public static String resolvestrToDate(String str){
        StringBuffer sb = new StringBuffer();
        String t1 = str.substring(0,4);
        String t2 = str.substring(4,6);
        String t3 = str.substring(6,8);
        String t4 = str.substring(8,10);
        String t5 = str.substring(10,12);
        String t6 = str.substring(12,14);
        String s = sb.append(t1).append("-")
                .append(t2).append("-")
                .append(t3)
                .append(" ")
                .append(t4).append(":")
                .append(t5).append(":")
                .append(t5).toString();
        return s;
    }


}
