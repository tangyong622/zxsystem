package com.zx.system.mapper;

import com.zx.system.entity.Chapter;

import java.util.List;


/**
 * Created by tangyong on 2018/3/10.
 */
public interface ChapterMapper extends Mapper{

    //检查是否存在课时
    int checkHas(Chapter course);

    List<Chapter> findListAll(Chapter chapter);
}
