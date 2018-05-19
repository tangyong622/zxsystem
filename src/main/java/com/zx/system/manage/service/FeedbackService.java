package com.zx.system.manage.service;

import com.zx.system.entity.Feedback;
import com.zx.system.mapper.FeedbackMapper;
import com.zx.system.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Service
public class FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;


    //分类列表
    public JsonResult findList(Map map) {
        //查看总数
        int count = feedbackMapper.findListCount(map);
        if (count > 0) {
            return new JsonResult(0, feedbackMapper.findList(map), "意见列表", count);
        }
        return new JsonResult(400, "暂无数据");
    }

    //新增意见
    public JsonResult form(Feedback feedback) {

        feedback.insert();
        feedbackMapper.insert(feedback);
        return new JsonResult(0, "意见发表成功");
    }


}
