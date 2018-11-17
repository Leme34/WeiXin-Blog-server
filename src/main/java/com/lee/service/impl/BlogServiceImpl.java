package com.lee.service.impl;

import com.google.gson.Gson;
import com.lee.dto.BlogDto;
import com.lee.mapper.BlogMapper;
import com.lee.mapper.BlogMapperCustom;
import com.lee.pojo.Blog;
import com.lee.service.BlogService;
import com.lee.vo.BlogVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapperCustom blogMapper;

    @Override
    public List<BlogVo> queryAllBlogs() {
        return blogMapper.getAllBlogVoList();
    }

    @Override
    public BlogDto saveBlog(BlogDto blogDto) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogDto, blog);  //若有id则把id也复制过去
        blog.setCreateTime(new Date());
        //把图片url的List转为json字符串保存到数据库
        Gson gson = new Gson();
        String imgListString = gson.toJson(blogDto.getImageList());
        //若没有blogId则是新增博文
        if (blogDto.getId() == null) {
            blog.setImageList(imgListString);  //设置图片列表
            blogMapper.saveBlog(blog);
            //设置数据库返回的blogId
            Long blogId = blog.getId();
            blogDto.setId(blogId);
//            System.out.println("inserted blogId=" + blogId);
        } else {  //否则有blogId是修改博文
            blog.setImageList(imgListString);   //设置图片列表
            blogMapper.updateByPrimaryKeySelective(blog);
            System.out.println("修改博文 blogId=" + blog.getId());
        }
        return blogDto;
    }
}
