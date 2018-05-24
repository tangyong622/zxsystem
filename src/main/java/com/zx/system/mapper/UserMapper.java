package com.zx.system.mapper;


import com.zx.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by tangyong on 2018/3/10.
 */
public interface UserMapper extends Mapper{

    //登录
    User doLogin(@Param("loginname") String loginname, @Param("password") String pawDES);

    //修改密码
    Integer forgetPwd(@Param("phone") String phone,@Param("password") String password);

    //根据号码查找用户
    User findByPhone(@Param("phone") String phone,@Param("id") String id);

    //检测课程是否已添加
    int getCourse(@Param("userId") String userId,@Param("courseId") String courseId);

    //添加课程
    Integer addCourse(@Param("userId") String userId,@Param("courseId") String courseId);

    //查看我的课程
    List<Map> getMyCourse(@Param("id") String id);
}
