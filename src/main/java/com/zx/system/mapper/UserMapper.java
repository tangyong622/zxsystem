package com.zx.system.mapper;


import com.zx.system.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by tangyong on 2018/3/10.
 */
public interface UserMapper extends Mapper{

    //登录
    User doLogin(@Param("loginname") String loginname, @Param("password") String pawDES);
}
