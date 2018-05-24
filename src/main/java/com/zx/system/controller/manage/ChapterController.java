package com.zx.system.controller.manage;

import com.zx.system.entity.Chapter;
import com.zx.system.service.ChapterService;
import com.zx.system.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Controller
@RequestMapping("/manage/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @RequestMapping("")
    public String index(){

        return "chapter";
    }

    //章节列表
    @RequestMapping("list")
    @ResponseBody
    public JsonResult list(Chapter chapter){

        return chapterService.list(chapter);
    }

    //章节列表
    @RequestMapping("findList")
    @ResponseBody
    public JsonResult findList(Chapter chapter){

        return chapterService.findList(chapter);
    }

    //编辑章节
    @RequestMapping("form")
    @ResponseBody
    public JsonResult form(Chapter chapter){

        return chapterService.form(chapter);
    }

    //删除章节
    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(Chapter chapter){

        return chapterService.delete(chapter);
    }


}
