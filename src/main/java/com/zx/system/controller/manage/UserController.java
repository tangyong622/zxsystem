package com.zx.system.controller.manage;

import com.zx.system.entity.User;
import com.zx.system.service.UserService;
import com.zx.system.util.JsonResult;
import com.zx.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Controller
@RequestMapping("/manage/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String index(){

        return "user";
    }

    //用户列表
    @RequestMapping("findList")
    @ResponseBody
    public JsonResult findList(String date,Integer page,Integer limit){
        Map map = new HashMap();
        if(StringUtils.isNotEmpty(date)){
            map.put("startTime",date.split(" ~ ")[0]);
            map.put("endTime",date.split(" ~ ")[1]);
        }

        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 10;
        }
        map.put("page",(page - 1) * limit);
        map.put("limit",limit);
        return userService.findList(map);
    }

    //编辑用户
    @RequestMapping("form")
    @ResponseBody
    public JsonResult form(User user){

        return userService.form(user);
    }

    //新增用户
    @RequestMapping("add")
    @ResponseBody
    public JsonResult add(String office,Integer num){

        return userService.add(office,num);
    }

    //初始化密码
    @RequestMapping("updatePwd")
    @ResponseBody
    public JsonResult updatePwd(User user){

        return userService.updatePwd(user);
    }

}
