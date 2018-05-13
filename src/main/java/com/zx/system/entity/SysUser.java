package com.zx.system.entity;

import lombok.Data;

@Data
public class SysUser extends Entity{


    private String password;//管理员密码
    private String loginname;//登录名
    private String username;//管理员姓名
    private String remark;//备注
    private String logintime;//最后登录时间
}