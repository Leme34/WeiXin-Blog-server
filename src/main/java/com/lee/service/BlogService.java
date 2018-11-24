package com.lee.service;

import com.lee.dto.BlogDto;
import com.lee.pojo.Blog;
import com.lee.pojo.User;
import com.lee.vo.BlogVo;

import java.util.List;

public interface BlogService {
    /**
     * 查询所有博文
     */
    List<BlogVo> queryAllBlogs();

    /**
     * 新增或修改(update)博文
     */
    BlogDto saveBlog(BlogDto blogDto);

    /**
     * 查询用户发表的博客
     */
    List<BlogVo> queryBlogsByUsername(String username);

    /**
     * 查询此博客详细
     */
    Blog queryBlogById(Long blogId);

    /**
     * 更新博客信息
     */
    void updateBlog(Blog blog);

    /**
     * 博客浏览量+1
     */
    void increaseReadSize(Long blogId);

}
