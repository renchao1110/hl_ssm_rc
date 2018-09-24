package com.hl95.ssm.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: hl_ssm_rc
 * @description: 获取当前请求ip地址
 * @author: renchao
 * @create: 2018-09-21 11:45
 **/
public class RemoteHostUtil {
    /**
     * 获取客户端IP
     * @param request
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request){

        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
}
