package com.zx.system.mapper;

import com.zx.system.entity.Category;
import com.zx.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by tangyong on 2018/3/10.
 */
public interface CategoryMapper extends Mapper{

    //检查是否存在
    Integer checkExist(Category category);

    //所有分类
    List<Category> findListAll(Category category);

    //检查是否存在课时
    int checkHas(Category category);

    //检查是否存在课时
    int checkHasVideo(Category category);
}
