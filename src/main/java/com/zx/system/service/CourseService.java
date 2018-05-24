package com.zx.system.service;

import com.zx.system.entity.Category;
import com.zx.system.entity.Course;
import com.zx.system.mapper.CategoryMapper;
import com.zx.system.mapper.CourseMapper;
import com.zx.system.util.FileUtil;
import com.zx.system.util.JsonResult;
import com.zx.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/20/020.
 */
@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    //课程列表
    public JsonResult findList(Course course) {
        //查看总数
        int count = courseMapper.findListCount(course);
        if (count > 0) {
            Integer page = course.getPage();
            if (page == null) {
                page = 1;
            }
            Integer limit = course.getLimit();
            if (limit == null) {
                limit = 10;
            }
            course.setPage((page - 1) * limit);
            course.setLimit(limit);
            return new JsonResult(0, courseMapper.findList(course), "课程列表", count);
        }
        return new JsonResult(400, "暂无数据");
    }

    //编辑课程
    @Transactional
    public JsonResult form(Course course) {
        if (StringUtils.isEmpty(course.getId())) {//新增
            course.insert();
            courseMapper.insert(course);
        } else {
            course.update();
            Course old = (Course)courseMapper.get(course.getId());
            if (!StringUtils.equals(course.getBigImg(), old.getBigImg())) {//更换大图，删除原来的图片
                FileUtil.delFile("static/" + old.getBigImg());
            }
            if (!StringUtils.equals(course.getImg(), old.getImg())) {//更换封面，删除原来的图片
                FileUtil.delFile("static/" + old.getImg());
            }
            courseMapper.update(course);
        }
        return new JsonResult(0, "编辑课程成功");
    }

    //删除视频
    public JsonResult delete(Course course) {
        courseMapper.delete(course.getId());
        FileUtil.delFile("static" + course.getBigImg());
        FileUtil.delFile("static" + course.getImg());
        return new JsonResult(0, "删除课程成功");
    }


    public JsonResult list(Course course) {

        return new JsonResult(courseMapper.findListAll(course.getCategoryId()));
    }

    //查看课程
    public JsonResult getCourseList() {
        List<Map> categories = categoryMapper.findListAll(null);
        for(Map category : categories){

            category.put("courseList",courseMapper.findListAll((String) category.get("id")));
        }
        return new JsonResult(categories);
    }

    //查看课程
    public JsonResult getCourseDetail(String id) {
        Course course = (Course)courseMapper.get(id);
        if(course == null){
            return new JsonResult(400,"没有数据");
        }
        //获得章节
        List<Map> chapterList = courseMapper.getChapterList(id);
        for(Map chapter:chapterList){
            //获取视频
            List<Map> videoList = courseMapper.geVideoList((String)chapter.get("id"));
            chapter.put("videoList",videoList);
        }
        Map result = new HashMap();
        result.put("id",course.getId());
        result.put("describe",course.getDescribe());
        result.put("bigImg",course.getId());
        result.put("img",course.getId());
        result.put("chapterList",chapterList);
        return new JsonResult(chapterList);
    }
}
