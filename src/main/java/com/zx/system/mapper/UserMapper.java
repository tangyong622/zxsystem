package com.zx.system.mapper;


import com.zx.system.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by tangyong on 2018/3/10.
 */
public interface UserMapper extends Mapper{

    //登录
    User doLogin(@Param("loginname") String loginname, @Param("password") String pawDES);

    //修改密码
    Integer forgetPwd(@Param("phone") String phone,@Param("password") String password);

    //根据号码查找用户
    User findByPhone(@Param("phone") String phone);
}
