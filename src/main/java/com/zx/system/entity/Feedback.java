package com.zx.system.entity;

import lombok.Data;

/**
 * Created by Administrator on 2018/5/15/015.
 */
@Data
public class Feedback extends Entity {

    private String userId;//用户ID
    private String view;//意见

    private String username;//发表用户

}
