package com.zx.system.entity;

import lombok.Data;

/**
 * Created by Administrator on 2018/5/16.
 */
@Data
public class FirstTitle extends Entity {

    private String name;//章节名称
    private String categoryId;//分类id
    private String sort;//排序
    private String remark;

    private String categoryName;//分类名
}
