package com.lee.service.impl;

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
//        System.out.println("loadUserByUsername userName=" + userName);
        User result = userMapper.getUserByUsername(username);
        //若没有此用户,抛出异常 但security规定不能返回null
        if(result==null){
            throw new UsernameNotFoundException("用户名或密码错误~");
        }
        return result;
    }

    @Override
    public User queryUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        userMapper.insertSelective(user);
    }

    @Override
    public boolean emailIsRegisted(String email) {
        User user = new User();
        user.setEmail(email);
        User result = userMapper.selectOne(user);
        return result == null ? false : true;
    }

    @Override
    public void updateUser(String username,User user) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        userMapper.updateByExampleSelective(user,example);
    }
}
