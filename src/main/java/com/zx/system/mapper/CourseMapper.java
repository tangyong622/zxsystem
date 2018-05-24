package com.zx.system.mapper;

import com.zx.system.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/20/020.
 */
public interface CourseMapper extends Mapper {

    //所有分类
    List<Map> findListAll(@Param("categoryId") String categoryId);

    //获得章节
    List<Map> getChapterList(@Param("id") String id);

    //获取视频
    List<Map> geVideoList(@Param("id") String id);
}
