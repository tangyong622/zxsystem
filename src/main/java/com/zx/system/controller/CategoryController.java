package com.zx.system.controller;

import com.zx.system.entity.Category;
import com.zx.system.service.CategoryService;
import com.zx.system.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Controller
@RequestMapping("/manage/course/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("")
    public String index(){

        return "category";
    }

    //分类列表
    @RequestMapping("findList")
    @ResponseBody
    public JsonResult findList(Category category){

        return categoryService.findList(category);
    }

    //编辑分类
    @RequestMapping("form")
    @ResponseBody
    public JsonResult form(Category category){

        return categoryService.form(category);
    }

    //删除分类
    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(Category category){

        return categoryService.delete(category);
    }


}
