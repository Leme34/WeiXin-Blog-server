package com.lee.service;

import com.github.pagehelper.PageInfo;
import com.lee.dto.BlogDto;
import com.lee.pojo.Blog;
import com.lee.vo.BlogVo;

import java.util.List;

public interface BlogService {
    /**
     * 分页查询所有博文
     */
    PageInfo<BlogVo> queryAllBlogs(Integer page, Integer pageSize);

    /**
     * 新增或修改(update)博文,并同步到elasticSearch
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
     * 查询此博客详细
     */
    BlogVo queryBlogVoById(Long blogId);

    /**
     * 更新博客信息
     */
    void updateBlog(Blog blog);

    /**
     * 博客收藏量+1
     */
    void increaseMarkSize(Long blogId);

    /**
     * 博客收藏量-1
     */
    void decreaseMarkSize(Long blogId);

    /**
     * 删除博客
     */
    void deleteBlog(Long blogId);

    /**
     * 查询用户收藏的博客
     */
    List<BlogVo> getMarkBlogByUserId(Long userId);



}
