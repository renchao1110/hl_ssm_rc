package com.hl95.ssm.util;

/**
 * 字符串工具类
 * @author renchao
 *
 */
public class StringUtils {
	
	/**
	 * 判断是否为空
	 * @param obj 要判断的参数
	 * @return 
	 */
	public static boolean isBlank(Object obj){
		if(obj==null){
			return true;
		}
		if(obj.toString().length()==0){
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * 判断不为空
	 * @param obj 要判断的参数
	 * @return 
	 */
	public static boolean isNotBlank(Object obj){
		if(obj==null){
			return false;
		}
		if(obj.toString().length()==0){
			return false;
		}
		return true;
	}
	
	
	/**
     * Object to String
     * @param obj
     * @return String or Null
     */
    public static String toString(Object obj) {
    	
    	if(obj==null) {
    		return null;
    	}
    	
    	return obj.toString();
    }
}
