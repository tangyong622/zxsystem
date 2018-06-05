package com.zx.system.controller.shop;

import com.zx.system.entity.Feedback;
import com.zx.system.entity.User;
import com.zx.system.service.FeedbackService;
import com.zx.system.service.UserService;
import com.zx.system.util.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/20/020.
 */
@RestController
@RequestMapping("/shop/user")
public class ShopUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FeedbackService feedbackService;

    //登录请求
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录请求", notes = "登录请求")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "loginname", value = "登录名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String"),
    })
    public JsonResult doLogin(String loginname, String password) {
        try {
            String pawDES = MD5Tools.getSHA256StrJava(password);
            //从数据库获取对应用户名密码的用户
            User user = userService.doLogin(loginname, pawDES);
            if (user == null) {
                return new JsonResult(400, "用户名或密码错误");
            }
            String token = StringUtils.getUUID();
            TokenUtils.setCache(token, user);
            Map map = new HashMap();
            map.put("token",token);
            map.put("user",user);
            return new JsonResult(0, map, "登录成功", 0);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(400, "用户名或密码错误");
        }
    }

    //获取用户信息
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = true, dataType = "String"),
    })
    public JsonResult doLogin(String token) {

        return TokenUtils.getCache(token);
    }

    //发送注册验证码
    @RequestMapping(value = "/registerCode", method = RequestMethod.POST)
    @ApiOperation(value = "发送注册验证码", notes = "发送注册验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, dataType = "String"),
    })
    public JsonResult registerCode(String phone) {
        User user = userService.findByPhone(phone);
        if (user != null) {
            return new JsonResult(400, "该号码已被注册，请确认");
        }
        JuheDemo.sendMsg(phone);
        return new JsonResult(0, "发送成功");
    }

    //完善资料
    @RequestMapping(value = "/perfectInfo", method = RequestMethod.POST)
    @ApiOperation(value = "完善资料", notes = "完善资料")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "真实姓名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "office", value = "学校/公司", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String"),
    })
    public JsonResult perfectInfo(String phone, String username, String office, String password) {

        User user = new User();
        user.setPhone(phone);
        user.setLoginname(phone);
        user.setUsername(username);
        user.setOffice(office);
        String pawDES = MD5Tools.getSHA256StrJava(password);
        user.setPassword(pawDES);
        return userService.form(user);
    }

    //验证短信
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ApiOperation(value = "验证短信", notes = "验证短信")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "code", value = "验证码", required = true, dataType = "String"),
    })
    public JsonResult validate(String phone, String code) {

        return TokenUtils.validate(phone, code);
    }

    //发送验证码
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, dataType = "String"),
    })
    public JsonResult sendCode(String phone) {

        JuheDemo.sendMsg(phone);
        return new JsonResult(0, "发送成功");
    }


    //忘记密码
    @RequestMapping(value = "/forgetPwd", method = RequestMethod.POST)
    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "code", value = "验证码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String"),
    })
    public JsonResult forgetPwd(String phone, String code, String password) {
        JsonResult result = TokenUtils.validate(phone, code);
        if (result.getCode() != 0) {
            return result;
        }
        String pawDES = MD5Tools.getSHA256StrJava(password);
        return userService.forgetPwd(phone, pawDES);
    }


    //添加课程
    @RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    @ApiOperation(value = "添加课程", notes = "添加课程")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "courseId", value = "课程id", required = true, dataType = "String"),
    })
    public JsonResult addCourse(String token, String courseId) {
        JsonResult result = TokenUtils.getCache(token);
        if (result.getCode() != 0) {
            return result;
        }
        User user = (User) result.getData();
        return userService.addCourse(user.getId(), courseId);
    }

    //查看我的课程
    @RequestMapping(value = "/getMyCourse", method = RequestMethod.POST)
    @ApiOperation(value = "查看我的课程", notes = "查看我的课程")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = true, dataType = "String"),
    })
    public JsonResult getMyCourse(String token) {
        JsonResult result = TokenUtils.getCache(token);
        if (result.getCode() != 0) {
            return result;
        }
        User user = (User) result.getData();
        return userService.getMyCourse(user.getId());
    }

    //添加意见
    @RequestMapping(value = "/addFeedback", method = RequestMethod.POST)
    @ApiOperation(value = "添加意见", notes = "添加意见")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "view", value = "意见内容", required = true, dataType = "String"),
    })
    public JsonResult addFeedback(String token, String view) {
        JsonResult result = TokenUtils.getCache(token);
        if (result.getCode() != 0) {
            return result;
        }
        User user = (User) result.getData();
        Feedback feedback = new Feedback();
        feedback.setUserId(user.getId());
        feedback.setView(view);
        return feedbackService.form(feedback);
    }

    //修改个人资料
    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改个人资料", notes = "修改个人资料")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "headPhoto", value = "用户头像", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户姓名", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "eduNo", value = "学号", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "sex", value = "性别", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "office", value = "学校/公司", required = false, dataType = "String"),
    })
    public JsonResult updateInfo(String token, String headPhoto, String username, String eduNo, String sex, String office) {
        JsonResult result = TokenUtils.getCache(token);
        if (result.getCode() != 0) {
            return result;
        }
        User user = (User) result.getData();
        User updUser  = new User();
        updUser.setId(user.getId());
        updUser.setHeadphoto(headPhoto);
        updUser.setUsername(username);
        updUser.setEduNo(eduNo);
        updUser.setSex(sex);
        updUser.setOffice(office);
        return userService.form(updUser);
    }

    //退出登录
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "退出登录", notes = "退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = true, dataType = "String"),
    })
    public JsonResult logout(String token){
        TokenUtils.clearCache(token);
        return new JsonResult(0,"退出成功");
    }

}
