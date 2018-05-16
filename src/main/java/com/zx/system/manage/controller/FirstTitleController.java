package com.zx.system.manage.controller;

import com.zx.system.entity.FirstTitle;
import com.zx.system.manage.service.FirstTitleService;
import com.zx.system.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Controller
@RequestMapping("/manage/firstTitle")
public class FirstTitleController {

    @Autowired
    private FirstTitleService firstTitleService;

    @RequestMapping("")
    public String index(){

        return "firstTitle";
    }

    //一级标题列表
    @RequestMapping("findList")
    @ResponseBody
    public JsonResult findList(FirstTitle firstTitle){

        return firstTitleService.findList(firstTitle);
    }

    //编辑一级标题
    @RequestMapping("form")
    @ResponseBody
    public JsonResult form(FirstTitle firstTitle){

        return firstTitleService.form(firstTitle);
    }

    //删除一级标题
    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(FirstTitle firstTitle){

        return firstTitleService.delete(firstTitle);
    }


}
