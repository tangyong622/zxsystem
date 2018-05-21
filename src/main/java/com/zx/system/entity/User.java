package com.zx.system.entity;

import lombok.Data;

/**
 * Created by Administrator on 2018/5/14.
 */
@Data
public class User extends Entity{

    private String loginname;//登录名
    private String password;//密码
    private String username;//姓名
    private String phone;//手机号
    private String office;//学校/单位
    private String headphoto;//头像
    private String eduNo;//学号
    private String sex;//性别
    private String logintime;//登录时间


}
