package com.lee.service.impl;

import com.lee.mapper.BlogMarkMapper;
import com.lee.pojo.BlogMark;
import com.lee.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MarkServiceImpl implements MarkService{

    @Autowired
    private BlogMarkMapper blogMarkMapper;

    @Override
    public void markBlog(Long userId, Long blogId) {
        BlogMark blogMark = new BlogMark();
        blogMark.setBlogId(blogId);
        blogMark.setUserId(userId);
        blogMark.setCreateTime(new Date());
        blogMarkMapper.insert(blogMark);
    }

    @Override
    public void cancelMarkBlog(Long userId, Long blogId) {
        BlogMark blogMark = new BlogMark();
        blogMark.setBlogId(blogId);
        blogMark.setUserId(userId);
        blogMarkMapper.delete(blogMark);
    }

    @Override
    public boolean isMarkBlog(Long userId, Long blogId) {
        BlogMark blogMark = new BlogMark();
        blogMark.setBlogId(blogId);
        blogMark.setUserId(userId);
        return blogMarkMapper.selectOne(blogMark)==null?false:true;
    }
}
