package com.zx.system.manage.service;

import com.zx.system.entity.Constant;
import com.zx.system.entity.SysUser;
import com.zx.system.mapper.LoginMapper;
import com.zx.system.util.JsonResult;
import com.zx.system.util.MD5Tools;
import com.zx.system.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    //登录请求
    public SysUser doLogin(String loginname, String password) {
        //查询用户
        SysUser sysUser = loginMapper.getUser(loginname,password);
        if(sysUser != null){
            SessionUtil.setSessionAttribute(Constant.LOGIN_USER,sysUser);
            SessionUtil.setSessionAttribute(Constant.LOGIN_USER_ID,sysUser.getId());
        }
        return sysUser;
    }
}
