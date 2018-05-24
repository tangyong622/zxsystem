package com.zx.system.entity;

import lombok.Data;

/**
 * Created by Administrator on 2018/5/15/015.
 */
@Data
public class Video extends Entity {

    private String name;
    private String chapterId;//章节Id
    private String url;//视频链接
    private Integer sort;
    private String remark;

    private String chapter;//章节
    private String courseId;//课程id
    private String course;//课程

}
