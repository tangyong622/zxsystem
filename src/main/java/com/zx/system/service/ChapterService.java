package com.zx.system.service;

import com.zx.system.entity.Chapter;
import com.zx.system.mapper.ChapterMapper;
import com.zx.system.util.JsonResult;
import com.zx.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/5/13/013.
 */
@Service
public class ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;

    //全部章节
    public JsonResult list(Chapter chapter) {

        return new JsonResult(chapterMapper.findListAll(chapter));
    }


    //章节列表
    public JsonResult findList(Chapter chapter) {
        //查看总数
        int count = chapterMapper.findListCount(chapter);
        if (count > 0) {
            Integer page = chapter.getPage();
            if (page == null) {
                page = 1;
            }
            Integer limit = chapter.getLimit();
            if (limit == null) {
                limit = 10;
            }
            chapter.setPage((page - 1) * 30);
            return new JsonResult(0, chapterMapper.findList(chapter), "章节列表", count);
        }
        return new JsonResult(400, "暂无数据");
    }

    //章节课程
    public JsonResult form(Chapter chapter) {
        if (StringUtils.isEmpty(chapter.getId())) {//新增
            chapter.insert();
            chapterMapper.insert(chapter);
        } else {
            chapter.update();
            chapterMapper.update(chapter);
        }
        return new JsonResult(0, "编辑章节成功");
    }

    //章节课程
    public JsonResult delete(Chapter chapter) {
        //检查是否存在课时
        if (chapterMapper.checkHas(chapter) > 0) {
            return new JsonResult(400, "该章节下存在视频");
        }
        chapterMapper.delete(chapter.getId());
        return new JsonResult(400, "删除课程成功");
    }
}