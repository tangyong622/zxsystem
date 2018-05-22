package com.zx.system.controller.shop;

import com.zx.system.entity.User;
import com.zx.system.service.UserService;
import com.zx.system.util.JsonResult;
import com.zx.system.util.MD5Tools;
import com.zx.system.util.StringUtils;
import com.zx.system.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/5/20/020.
 */
@RestController
@RequestMapping("/shop/user")
public class ShopUserController {

    @Autowired
    private UserService userService;

    //登录请求
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public JsonResult doLogin(String loginname,String password){
        try {
            String pawDES = MD5Tools.getSHA256StrJava(password);
            //从数据库获取对应用户名密码的用户
            User user = userService.doLogin(loginname, pawDES);
            if (user == null) {
                return new JsonResult(400,"用户名或密码错误");
            }
            String token = StringUtils.getUUID();
            TokenUtils.setCache(token,user);
            return new JsonResult(0,token,"登录成功",0);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(400,"用户名或密码错误");
        }
    }

    //获取用户信息
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public JsonResult doLogin(String token){

        return TokenUtils.getCache(token);
    }

    //发送注册验证码
    @RequestMapping(value = "/registerCode",method = RequestMethod.POST)
    public JsonResult registerCode(String phone){
        User user = userService.findByPhone(phone);
        if(user != null){
            return new JsonResult(400,"该号码已被注册，请确认");
        }
        // TODO: 2018/5/21/021 发短信
        long time = (long) (1000 * 60 * 60 * 0.5);
        TokenUtils.setCache(phone,"123456",time);
        return new JsonResult(0,"发送成功");
    }

    //完善资料
    @RequestMapping(value = "/perfectInfo",method = RequestMethod.POST)
    public JsonResult perfectInfo(String phone,String username,String office,String password){

        User user = new User();
        user.setPhone(phone);
        user.setLoginname(phone);
        user.setUsername(username);
        user.setOffice(office);
        user.setPassword(password);
        return userService.form(user);
    }

    //验证短信
    @RequestMapping(value = "/validate",method = RequestMethod.POST)
    public JsonResult validate(String phone,String code){

        return TokenUtils.validate(phone,code);
    }

    //发送验证码
    @RequestMapping(value = "/sendCode",method = RequestMethod.POST)
    public JsonResult sendCode(String phone){
        // TODO: 2018/5/21/021 发短信
        long time = (long) (1000 * 60 * 60 * 0.5);
        TokenUtils.setCache(phone,"123456",time);
        return new JsonResult(0,"发送成功");
    }



    //忘记密码
    @RequestMapping(value = "/forgetPwd",method = RequestMethod.POST)
    public JsonResult forgetPwd(String phone,String code,String password){
        JsonResult result = TokenUtils.validate(phone,code);
        if(result.getCode() != 0){
            return result;
        }
        return userService.forgetPwd(phone,password);
    }

}
