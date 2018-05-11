package com.zx.system.mapper;

import com.zx.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by tangyong on 2018/3/10.
 */
public interface UserMapper extends Mapper{

    //修改登录时间
    User getUser(@Param("name") String name, @Param("password") String password);

    //修改登录时间
    Integer updateLoginTime(@Param("id") String id);

    //修改密码
    Integer updatePwd(User user);

    //用户列表
    List<User> userList(User user);
}
