package com.lee.mapper;

import com.lee.pojo.User;
import com.lee.utils.MyMapper;

public interface UserMapperCustom extends MyMapper<User> {

    //关联查出用户权限(角色)
    User getUserByUsername(String username);

}