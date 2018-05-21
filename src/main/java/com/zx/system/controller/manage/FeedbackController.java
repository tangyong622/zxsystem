package com.zx.system.controller.manage;

import com.zx.system.entity.Feedback;
import com.zx.system.service.FeedbackService;
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
@RequestMapping("/manage/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping("")
    public String index(){

        return "feedback";
    }

    //意见列表
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
        return feedbackService.findList(map);
    }

    //新增意见
    @RequestMapping("form")
    @ResponseBody
    public JsonResult form(Feedback feedback){

        return feedbackService.form(feedback);
    }

}
