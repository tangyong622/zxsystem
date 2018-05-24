package com.zx.system.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15/015.
 */
@Data
public class Category extends Entity {

    private String name;
    private Integer sort;
    private String remark;
}
