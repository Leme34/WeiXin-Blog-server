package com.lee.service.impl;

import com.lee.mapper.UserMapper;
import com.lee.mapper.UserMapperCustom;
import com.lee.pojo.User;
import com.lee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserMapperCustom userMapper;

    //认证逻辑
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User result = userMapper.getUserByUsername(username);
        return result;
    }
}
