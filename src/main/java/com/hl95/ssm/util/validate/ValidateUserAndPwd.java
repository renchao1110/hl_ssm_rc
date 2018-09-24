package com.hl95.ssm.util.validate;

import com.hl95.ssm.dao.UserMapper;
import com.hl95.ssm.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public  boolean validateUserAndPwd(Map<String,Object> params) {
            User user = userMapper.findUserBySnid((String) params.get("sn"));
            if (user != null) {
                String temp = (String) params.get("sn") + params.get("pwd");
                String token = DigestUtils.md5Hex(temp);
                if (token.equals(user.getPwd())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
    }
}
