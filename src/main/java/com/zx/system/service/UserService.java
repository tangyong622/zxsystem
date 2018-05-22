package com.zx.system.service;

import com.zx.system.entity.User;
import com.zx.system.mapper.UserMapper;
import com.zx.system.util.JsonResult;
import com.zx.system.util.MD5Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    //用户列表
    public JsonResult findList(Map map) {
        //查看总数
        int count = userMapper.findListCount(map);
        if (count > 0) {
            return new JsonResult(0, userMapper.findList(map), "用户列表", count);
        }
        return new JsonResult(400, "暂无数据");
    }

    //修改用户
    public JsonResult form(User user) {
        user.update();
        userMapper.update(user);
        return new JsonResult(0, "修改用户成功");
    }

    //新增用户
    public JsonResult add(String office, Integer num) {
        User user = new User();
        long mid = new Date().getTime();
        for (int i = 0; i < num; i++) {
            user.setLoginname("xz" + mid);
            user.setOffice(office);
            //密码进行加密处理  明文为  password
            String pawDES = MD5Tools.getSHA256StrJava("123456");
            user.setPassword(pawDES);
            user.insert();
            userMapper.insert(user);
            mid++;
        }
        return new JsonResult(0, "生成" + num + "用户");
    }

    //初始化密码
    public JsonResult updatePwd(User user) {
        String pawDES = MD5Tools.getSHA256StrJava("123456");
        user.setPassword(pawDES);
        userMapper.update(user);
        return new JsonResult(0, "初始化密码成功");
    }

    //登录
    public User doLogin(String loginname, String pawDES) {

        return userMapper.doLogin(loginname,pawDES);
    }

    //忘记密码
    public JsonResult forgetPwd(String phone, String password) {
        userMapper.forgetPwd(phone,password);
        return new JsonResult(0, "修改密码成功");
    }

    //根据号码查找用户
    public User findByPhone(String phone) {

        return userMapper.findByPhone(phone);
    }
}
