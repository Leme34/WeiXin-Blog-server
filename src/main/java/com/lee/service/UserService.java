package com.lee.service;

import com.lee.pojo.User;

public interface UserService {

    /**
     * 查询用户信息
     */
    User queryUserByUsername(String username);

    /**h
     * 注册用户
     */
    void saveUser(User user);

    /**
     * 查询该邮箱是否已被注册
     */
    boolean emailIsRegisted(String email);

    /**
     * 更新用户信息,请求的bean中null的属性不会被修改
     */
    void updateUser(String username,User user);
}
