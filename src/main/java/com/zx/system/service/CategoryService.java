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
        List<Category> data = new ArrayList();
        List<Category> allList = categoryMapper.findListAll(category);
        for (Category cate : allList) {
            if (StringUtils.equals("1",cate.getId())) {//取一级
                data.add(cate);
            }
            for (Category cate2 : allList) {//取子菜单
                if (StringUtils.equals(cate.getId(),cate2.getPid())) {
                    if (cate.getChildren() == null) {
                        cate.setChildren(new ArrayList<Category>());
                    }
                    cate.getChildren().add(cate2);
                }
            }
        }
        return new JsonResult(data);
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
            category.setPage((page - 1) * 30);
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
            return new JsonResult(400, "该分类下子集存在");
        }
        //检查是否存在课时
        if (categoryMapper.checkHasVideo(category) > 0) {
            return new JsonResult(400, "该分类下有课程存在");
        }
        categoryMapper.delete(category.getId());
        return new JsonResult(400, "删除分类成功");
    }

}
