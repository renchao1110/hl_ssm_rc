package com.hl95.ssm.util.resolve;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 解析接口请求数据
 * @author: rc
 * @create: 2018-09-18 17:14
 **/
public class ParamsResolve {

    public static Map<String,Object> getParams(HttpServletRequest request){
        Map<String,Object> params = new HashMap<>(16);
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            if (name!=null){
                String value = request.getParameter(name);
                params.put(name,value);
            }

        }
        return params;
    }
}
