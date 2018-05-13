package com.zx.system.manage.controller;

import com.zx.system.manage.service.LoginService;
import com.zx.system.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Controller
@RequestMapping("/sys")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){

        return "login";
    }

    //登录请求
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public JsonResult doLogin(String loginname,String password){

        return loginService.doLogin(loginname,password);
    }

}
