package com.lee.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.lee.dto.BlogDto;
import com.lee.mapper.BlogMapperCustom;
import com.lee.pojo.Blog;
import com.lee.service.BlogService;
import com.lee.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapperCustom blogMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Value("${topicName}")
    private String topicName;

    @Override
    public PageInfo<BlogVo> queryAllBlogs(Integer page,Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<BlogVo> blogVoList = blogMapper.getAllBlogVoList();
        PageInfo<BlogVo> pageInfo = new PageInfo<>(blogVoList);
        return pageInfo;
    }

    @Override
    public BlogDto saveBlog(BlogDto blogDto) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogDto, blog);  //若有id则把id也复制过去
        log.info("复制完后的blog:"+blog);
        blog.setCreateTime(new Date());
        //把图片url的List转为json字符串保存到数据库
        Gson gson = new Gson();
        String imgListString = gson.toJson(blogDto.getImageList());
        //若没有blogId则是新增博文
        if (blogDto.getId() == null) {
            blog.setImageList(imgListString);  //设置图片列表
            blogMapper.saveBlog(blog);
            //设置数据库返回的blogId
            blogDto.setId(blog.getId());
        } else {  //否则有blogId是修改博文
            blog.setImageList(imgListString);   //设置图片列表
            blogMapper.updateByPrimaryKeySelective(blog);
        }
        //发消息通知添加/更新索引
        jmsMessagingTemplate.convertAndSend(topicName,blog.getId()+"");
        return blogDto;
    }

    @Override
    public List<BlogVo> queryBlogsByUsername(String username) {
        return blogMapper.queryBlogByUsername(username);
    }

    @Override
    public Blog queryBlogById(Long blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }

    @Override
    public BlogVo queryBlogVoById(Long blogId) {
        return blogMapper.queryBlogVoById(blogId);
    }

    @Override
    public void updateBlog(Blog blog) {
        blogMapper.updateByPrimaryKeySelective(blog);
    }

    @Override
    public void increaseMarkSize(Long blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        blog.setMarkSize(blog.getMarkSize()+1);
        blogMapper.updateByPrimaryKeySelective(blog);
    }

    @Override
    public void decreaseMarkSize(Long blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        blog.setMarkSize(blog.getMarkSize()-1);
        blogMapper.updateByPrimaryKeySelective(blog);
    }

    @Override
    public void deleteBlog(Long blogId) {
        //在数据库端做级联删除
        blogMapper.deleteByPrimaryKey(blogId);
    }

    @Override
    public List<BlogVo> getMarkBlogByUserId(Long userId) {
        return blogMapper.queryMarkBlogByUserId(userId);
    }

}
