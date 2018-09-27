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
}
