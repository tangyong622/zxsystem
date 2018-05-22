package com.zx.system.service;

import com.zx.system.entity.Category;
import com.zx.system.mapper.CategoryMapper;
import com.zx.system.util.JsonResult;
import com.zx.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //所有分类
    public JsonResult list(Category category) {

        return new JsonResult(categoryMapper.findListAll(category));
    }

    //分类列表
    public JsonResult findList(Category category) {
        //查看总数
        int count = categoryMapper.findListCount(category);
        if (count > 0) {
            Integer page = category.getPage();
            if (page == null) {
                page = 1;
            }
            Integer limit = category.getLimit();
            if (limit == null) {
                limit = 10;
            }
            category.setPage((page - 1) * limit);
            category.setLimit(limit);
            return new JsonResult(0, categoryMapper.findList(category), "分类列表", count);
        }
        return new JsonResult(400, "暂无数据");
    }

    //编辑分类
    public JsonResult form(Category category) {
        //检查是否存在
        if (categoryMapper.checkExist(category) > 0) {
            return new JsonResult(400, "该名称已存在");
        }
        if (StringUtils.isEmpty(category.getId())) {//新增
            category.insert();
            categoryMapper.insert(category);
        } else {
            category.update();
            categoryMapper.update(category);
        }
        return new JsonResult(0, "编辑分类成功");
    }

    //删除分类
    public JsonResult delete(Category category) {
        //检查是否存在课时
        if (categoryMapper.checkHas(category) > 0) {
            return new JsonResult(400, "该分类有课程存在");
        }
        categoryMapper.delete(category.getId());
        return new JsonResult(0, "删除分类成功");
    }

}
