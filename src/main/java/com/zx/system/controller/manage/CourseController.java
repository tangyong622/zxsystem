package com.zx.system.controller.manage;

import com.zx.system.entity.Course;
import com.zx.system.service.CourseService;
import com.zx.system.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/5/20/020.
 */
@Controller
@RequestMapping("/manage/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("")
    public String index(){

        return "course";
    }

    //课程列表
    @RequestMapping("list")
    @ResponseBody
    public JsonResult list(Course course){

        return courseService.list(course);
    }

    //课程列表
    @RequestMapping("findList")
    @ResponseBody
    public JsonResult findList(Course course){

        return courseService.findList(course);
    }

    //编辑课程
    @RequestMapping("form")
    @ResponseBody
    public JsonResult form(Course course){

        return courseService.form(course);
    }

    //删除课程
    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(Course course){

        return courseService.delete(course);
    }

}
