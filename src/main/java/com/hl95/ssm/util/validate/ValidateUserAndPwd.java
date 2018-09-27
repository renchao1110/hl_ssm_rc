package com.hl95.ssm.util.validate;

import com.hl95.ssm.dao.UserMapper;
import com.hl95.ssm.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @program: hl_ssm_rc
 * @description: 用来验证当前用户是否合法
 * @author: renchao
 * @create: 2018-09-20 11:54
 **/
@Component
public class ValidateUserAndPwd {
    @Autowired
    private UserMapper userMapper;
    public  boolean validateUserAndPwd(Map<String,Object> params, HttpSession session) {
        User user = (User)session.getAttribute("user");
        //向session中保存当前用户信息，后面方法会从session中获取用户信息
        if (user==null){
            user = userMapper.findUserBySnid((String) params.get("sn"));
            session.setAttribute("user",user);
        }
        //为了验证每次用户访问参数是否正确，每次再从数据库重新查询
        user = userMapper.findUserBySnid((String) params.get("sn"));
            if (user != null) {
                //String temp = (String) params.get("sn") + params.get("pwd");
                //String token = DigestUtils.md5Hex(temp);
                String pwd = (String)params.get("pwd");
                if (pwd.equals(user.getPwd())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
    }
}
