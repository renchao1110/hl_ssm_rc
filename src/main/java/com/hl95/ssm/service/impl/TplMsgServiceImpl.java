package com.hl95.ssm.service.impl;

import com.hl95.ssm.dao.MsgTempletMapper;
import com.hl95.ssm.dao.UserMapper;
import com.hl95.ssm.entity.MsgTemplet;
import com.hl95.ssm.entity.User;
import com.hl95.ssm.service.TplMsgService;
import com.hl95.ssm.util.enums.Msg_Tpl_Enums;
import com.hl95.ssm.util.enums.SendTplSmsEnums;
import com.hl95.ssm.util.resolve.ObjToMap;
import com.hl95.ssm.util.resolve.ParamsResolve;
import com.hl95.ssm.util.validate.BaseValidateParams;
import com.hl95.ssm.util.validate.Msg_Tpl_ValidateParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 处理模板报备的所有业务
 * @author: rc
 * @create: 2018-09-19 11:09
 **/
@Service("tplAddService")
public class TplMsgServiceImpl implements TplMsgService {
    private static final String SUCCESS = "提交成功";
    private static final String REASON = "reason";
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MsgTempletMapper msgTempletMapper;
    @Resource(name="msg_Tpl_ValidateParams")
    private BaseValidateParams msg_Tpl_ValidateParams;
    @Override
    public Map<String, Object> tpladd(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        List<Map<String, Object> > list = new ArrayList<>();
        Map<String, Object> params = ParamsResolve.getParams(request);
        Map<String, Object> validateResult = msg_Tpl_ValidateParams.validateAddParams(params);
        if (SUCCESS.equals(validateResult.get(REASON))){
            User user = userMapper.findUserBySnid((String) params.get("sn"));
            if(user!=null){
                String pwd = (String)params.get("pwd");
                String tpl_content = (String) params.get("tpl_content");
                if (pwd.equals(user.getPwd())){
                    HttpSession session = request.getSession();
                    if(session.getAttribute("user")==null){
                        session.setAttribute("user",user);
                    }
                    /*if (Msg_Tpl_ValidateParams.regx(tpl_content)){
                        result.put(SendTplSmsEnums.Status_00.getKey(),SendTplSmsEnums.Status_00.getValue());
                        result.put(SendTplSmsEnums.Reason_00.getKey(),SendTplSmsEnums.Reason_00.getValue());
                        result.put("tpl_content",tpl_content);
                        msgTempletMapper.saveMsgTemplet(result);
                        result.remove("tpl_content");
                        list.add(result);
                        map.put("result",list);
                        return map;
                    }*/
                    result.put(SendTplSmsEnums.Status_00.getKey(),SendTplSmsEnums.Status_00.getValue());
                    result.put(SendTplSmsEnums.Reason_00.getKey(),SendTplSmsEnums.Reason_00.getValue());
                    result.put("tpl_content",tpl_content);
                    result.put(SendTplSmsEnums.State_02.getKey(),SendTplSmsEnums.State_02.getValue());
                    result.put(SendTplSmsEnums.Opinion_02.getKey(),SendTplSmsEnums.Opinion_02.getValue());
                    msgTempletMapper.saveMsgTemplet(result);
                    result.remove("tpl_content");
                    result.remove("state");
                    result.remove("opinion");
                    list.add(result);
                    map.put("result",list);
                    return map;
                }else {
                    result.put(SendTplSmsEnums.Status_01.getKey(),SendTplSmsEnums.Status_01.getValue());
                    result.put(SendTplSmsEnums.Reason_01.getKey(),SendTplSmsEnums.Reason_01.getValue());
                    list.add(result);
                    map.put("result",list);
                    return map;
                }
            }else{
                result.put(SendTplSmsEnums.Status_01.getKey(),SendTplSmsEnums.Status_01.getValue());
                result.put(SendTplSmsEnums.Reason_01.getKey(),SendTplSmsEnums.Reason_01.getValue());
                list.add(result);
                map.put("result",list);
                return map;
            }
        }
        list.add(validateResult);
        map.put("result",list);
        return map;
    }



    @Override
    public Map<String, Object> gettpl(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        List<Map<String, Object> > list = new ArrayList<>();
        Map<String, Object> params = ParamsResolve.getParams(request);
        Map<String, Object> validateResult = msg_Tpl_ValidateParams.validateGetParams(params);
        if (SUCCESS.equals(validateResult.get(REASON))){
            User user = userMapper.findUserBySnid((String) params.get("sn"));
            if(user!=null){
                String pwd = (String)params.get("pwd");
                if (pwd.equals(user.getPwd())){
                    HttpSession session = request.getSession();
                    if(session.getAttribute("user")==null){
                        session.setAttribute("user",user);
                    }
                    String tpl_id = (String) params.get("tpl_id");
                    if (tpl_id!=null&&!"".equals(tpl_id)){
                        MsgTemplet msgTemplet = msgTempletMapper.selectByPrimaryKey(tpl_id);
                        if (msgTemplet==null){
                            result.put(SendTplSmsEnums.Status_04.getKey(),SendTplSmsEnums.Status_04.getValue());
                            result.put(SendTplSmsEnums.Reason_04.getKey(),SendTplSmsEnums.Reason_04.getValue());
                            list.add(result);
                            map.put("result",list);
                            return map;
                        }else{
                            Map<String, Object> temp = ObjToMap.objToMap(msgTemplet);
                            list.add(temp);
                            map.put("result",list);
                            return map;
                        }
                    }else {
                        List<MsgTemplet> msgTemplets = msgTempletMapper.getManyMsgTemplets();
                        if (msgTemplets.size()==0){
                            map.put("result","没有可用模板，请先报备模板");
                            return map;
                        }
                        map = ObjToMap.listToMap(msgTemplets);
                        /*list.add(result);
                        map.put("result",list);*/
                        return map;
                    }
                }else {
                    result.put(SendTplSmsEnums.Status_01.getKey(),SendTplSmsEnums.Status_01.getValue());
                    result.put(SendTplSmsEnums.Reason_01.getKey(),SendTplSmsEnums.Reason_01.getValue());
                    list.add(result);
                    map.put("result",list);
                    return map;
                }
            }else{
                result.put(SendTplSmsEnums.Status_01.getKey(),SendTplSmsEnums.Status_01.getValue());
                result.put(SendTplSmsEnums.Reason_01.getKey(),SendTplSmsEnums.Reason_01.getValue());
                list.add(result);
                map.put("result",list);
                return map;
            }
        }
        list.add(validateResult);
        map.put("result",list);
        return map;
    }

    @Override
    public Map<String, Object> updatetpl(HttpServletRequest request) {
        Map<String, Object> params = ParamsResolve.getParams(request);
        Map<String, Object> validateResult = msg_Tpl_ValidateParams.validateUpdateParams(params);
        Map<String, Object> result = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        List<Map<String, Object> > list = new ArrayList<>();
        if (SUCCESS.equals(validateResult.get(REASON))){
            User user = userMapper.findUserBySnid((String) params.get("sn"));
            if(user!=null){
                /*String temp = (String)params.get("sn")+params.get("pwd");
                String token = DigestUtils.md5Hex(temp);*/
                String pwd = (String)params.get("pwd");
                if (pwd.equals(user.getPwd())){
                    HttpSession session = request.getSession();
                    if(session.getAttribute("user")==null){
                        session.setAttribute("user",user);
                    }
                    String tpl_id = (String) params.get("tpl_id");
                    if (tpl_id!=null&&!"".equals(tpl_id)){
                        Map<String,Object> temps = new HashMap<>(16);
                        temps.put("tpl_id",tpl_id);
                        temps.put("tpl_content",params.get("tpl_content"));
                        temps.put(SendTplSmsEnums.Status_00.getKey(),SendTplSmsEnums.Status_00.getValue());
                        temps.put(SendTplSmsEnums.Reason_00.getKey(),SendTplSmsEnums.Reason_00.getValue());
                        temps.put(SendTplSmsEnums.State_02.getKey(),SendTplSmsEnums.State_02.getValue());
                        temps.put(SendTplSmsEnums.Opinion_02.getKey(),SendTplSmsEnums.Opinion_02.getValue());
                        int i = msgTempletMapper.updateByParams(temps);
                        if (i==1){
                            //map = ObjToMap.resolveMap(temps);
                            temps.remove("tpl_content");
                            temps.remove("state");
                            temps.remove("opinion");
                            list.add(temps);
                            map.put("result",list);
                            return map;
                        }else {
                            result.put(SendTplSmsEnums.Status_04.getKey(),SendTplSmsEnums.Status_04.getValue());
                            result.put(SendTplSmsEnums.Reason_04.getKey(),SendTplSmsEnums.Reason_04.getValue());
                            list.add(result);
                            map.put("result",list);
                            return map;
                        }
                    }
                }else {
                    result.put(SendTplSmsEnums.Status_01.getKey(),SendTplSmsEnums.Status_01.getValue());
                    result.put(SendTplSmsEnums.Reason_01.getKey(),SendTplSmsEnums.Reason_01.getValue());
                    list.add(result);
                    map.put("result",list);
                    return map;
                }
            }else{
                result.put(SendTplSmsEnums.Status_01.getKey(),SendTplSmsEnums.Status_01.getValue());
                result.put(SendTplSmsEnums.Reason_01.getKey(),SendTplSmsEnums.Reason_01.getValue());
                list.add(result);
                map.put("result",list);
                return map;
            }
        }
        list.add(validateResult);
        map.put("result",list);
        return map;
    }

    @Override
    public Map<String, Object> tplDel(HttpServletRequest request) {
        Map<String, Object> params = ParamsResolve.getParams(request);
        Map<String, Object> validateResult = msg_Tpl_ValidateParams.validateDeleteParams(params);
        Map<String, Object> result = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        List<Map<String, Object> > list = new ArrayList<>();
        if (SUCCESS.equals(validateResult.get(REASON))){
            User user = userMapper.findUserBySnid((String) params.get("sn"));
            if(user!=null){
                String pwd = (String)params.get("pwd");
                if (pwd.equals(user.getPwd())){
                    HttpSession session = request.getSession();
                    if(session.getAttribute("user")==null){
                        session.setAttribute("user",user);
                    }
                    String tpl_id = (String) params.get("tpl_id");
                    if (tpl_id!=null&&!"".equals(tpl_id)){
                        int i = msgTempletMapper.deleteByPrimaryKey(tpl_id);
                        if (i==1){
                            Map<String,Object> temps = new HashMap<>(16);
                            temps.put("tpl_id",tpl_id);
                            temps.put(SendTplSmsEnums.Status_00.getKey(),SendTplSmsEnums.Status_00.getValue());
                            temps.put(SendTplSmsEnums.Reason_00.getKey(),SendTplSmsEnums.Reason_00.getValue());
                            list.add(temps);
                            map.put("result",list);
                            return map;
                        }else {
                            result.put(SendTplSmsEnums.Status_04.getKey(),SendTplSmsEnums.Status_04.getValue());
                            result.put(SendTplSmsEnums.Reason_04.getKey(),SendTplSmsEnums.Reason_04.getValue());
                            list.add(result);
                            map.put("result",list);
                            return map;
                        }
                    }
                }else {
                    result.put(SendTplSmsEnums.Status_01.getKey(),SendTplSmsEnums.Status_01.getValue());
                    result.put(SendTplSmsEnums.Reason_01.getKey(),SendTplSmsEnums.Reason_01.getValue());
                    list.add(result);
                    map.put("result",list);
                    return map;
                }
            }else{
                result.put(SendTplSmsEnums.Status_01.getKey(),SendTplSmsEnums.Status_01.getValue());
                result.put(SendTplSmsEnums.Reason_01.getKey(),SendTplSmsEnums.Reason_01.getValue());
                list.add(result);
                map.put("result",list);
                return map;
            }
        }
        list.add(validateResult);
        map.put("result",list);
        return map;
    }

}


