package com.zx.system.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15/015.
 */
@Data
public class Category extends Entity {

    private String name;
    private String pid;
    private Integer sort;
    private String remark;

    private List<Category> children;
    private String categoryId;//
    private String pname;//所有上级名称
    private String parentName;//直接上级名称
}
