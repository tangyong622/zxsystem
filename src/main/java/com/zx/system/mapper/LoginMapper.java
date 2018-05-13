package com.zx.system.mapper;

import com.zx.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by tangyong on 2018/3/10.
 */
public interface LoginMapper extends Mapper{

    //查询用户
    SysUser getUser(@Param("name") String name, @Param("password") String password);

    //修改登录时间
    Integer updateLoginTime(@Param("id") String id);

    //修改密码
    Integer updatePwd(SysUser user);

}
