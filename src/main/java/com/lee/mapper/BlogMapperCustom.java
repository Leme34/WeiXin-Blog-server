package com.lee.mapper;

import com.lee.pojo.Blog;
import com.lee.utils.MyMapper;
import com.lee.vo.BlogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapperCustom extends MyMapper<Blog> {

    /**
     * 查询博客列表
     */
    List<BlogVo> getAllBlogVoList();

    /**
     * 插入blog返回blogId
     */
    Integer saveBlog(Blog blog);

    /**
     * 查询用户主页的博客列表
     */
    List<BlogVo> queryBlogByUsername(String username);

    /**
     * 查询此用户收藏的博客
     */
    List<BlogVo> queryMarkBlogByUserId(Long userId);

    /**
     * 查询此博客详细
     */
    BlogVo queryBlogVoById(Long blogId);

}