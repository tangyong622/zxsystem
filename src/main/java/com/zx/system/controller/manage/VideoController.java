package com.zx.system.controller.manage;


import com.zx.system.entity.Video;
import com.zx.system.service.VideoService;
import com.zx.system.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Controller
@RequestMapping("/manage/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @RequestMapping("")
    public String index(){

        return "video";
    }

    //视频列表
    @RequestMapping("findList")
    @ResponseBody
    public JsonResult findList(Video video){

        return videoService.findList(video);
    }

    //编辑视频
    @RequestMapping("form")
    @ResponseBody
    public JsonResult form(Video video){

        return videoService.form(video);
    }

    //删除视频
    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(Video video){

        return videoService.delete(video);
    }

}
