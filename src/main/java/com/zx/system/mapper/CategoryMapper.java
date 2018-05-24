package com.zx.system.mapper;

import com.zx.system.entity.Category;

import java.util.List;
import java.util.Map;


/**
 * Created by tangyong on 2018/3/10.
 */
public interface CategoryMapper extends Mapper{

    //检查是否存在
    Integer checkExist(Category category);

    //所有分类
    List<Map> findListAll(Category category);

    //检查是否存在课时
    int checkHas(Category category);

}
