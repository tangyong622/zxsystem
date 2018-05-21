package com.zx.system.entity;

import lombok.Data;

/**
 * Created by Administrator on 2018/5/16.
 */
@Data
public class Video extends Entity {

    private String title;//标题
    private String categoryId;//分类id
    private String describe;//描述
    private String bigImg;//大图
    private String img;//封面
    private String isEdu;//是否教学视频 0.否 1.是
    private String isHead;//是否推荐首页 0.否 1.是
    private String url;//视频链接
    private String remark;
    private String sort;

    private String name;//分类名
    private String pname;//所有分类名
}
