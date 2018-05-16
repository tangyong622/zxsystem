package com.zx.system.mapper;

import com.zx.system.entity.FirstTitle;


/**
 * Created by tangyong on 2018/3/10.
 */
public interface FirstTitleMapper extends Mapper{

    //检查是否存在
    Integer checkExist(FirstTitle firstTitle);

    //检查是否存在课时
    int checkHas(FirstTitle firstTitle);
}
