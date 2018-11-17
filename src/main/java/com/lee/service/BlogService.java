package com.lee.service;

import com.lee.dto.BlogDto;
import com.lee.pojo.Blog;
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

}
