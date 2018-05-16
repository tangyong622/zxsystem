package com.zx.system.manage.service;

import com.zx.system.entity.FirstTitle;
import com.zx.system.mapper.FirstTitleMapper;
import com.zx.system.util.JsonResult;
import com.zx.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Service
public class FirstTitleService {

    @Autowired
    private FirstTitleMapper firstTitleMapper;


    //分类列表
    public JsonResult findList(FirstTitle firstTitle) {
        //查看总数
        int count = firstTitleMapper.findListCount(firstTitle);
        if (count > 0) {
            Integer page = firstTitle.getPage();
            if (page == null) {
                page = 1;
            }
            Integer limit = firstTitle.getLimit();
            if (limit == null) {
                limit = 10;
            }
            firstTitle.setPage((page - 1) * 30);
            return new JsonResult(0, firstTitleMapper.findList(firstTitle), "一级标题列表", count);
        }
        return new JsonResult(400, "暂无数据");
    }

    //编辑一级标题
    public JsonResult form(FirstTitle firstTitle) {
        //检查是否存在
        if (firstTitleMapper.checkExist(firstTitle) > 0) {
            return new JsonResult(400, "该名称已存在");
        }
        if (StringUtils.isEmpty(firstTitle.getId())) {//新增
            firstTitle.insert();
            firstTitleMapper.insert(firstTitle);
        } else {
            firstTitle.update();
            firstTitleMapper.update(firstTitle);
        }
        return new JsonResult(0, "编辑一级标题成功");
    }

    //删除一级标题
    public JsonResult delete(FirstTitle firstTitle) {
        //检查是否存在课时
        if (firstTitleMapper.checkHas(firstTitle) > 0) {
            return new JsonResult(400, "该名称已存在");
        }
        firstTitleMapper.delete(firstTitle.getId());
        return new JsonResult(400, "删除一级标题成功");
    }
}
