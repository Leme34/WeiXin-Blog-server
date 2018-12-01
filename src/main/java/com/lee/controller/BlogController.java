package com.lee.controller;

import com.github.pagehelper.PageInfo;
import com.lee.dto.BlogDto;
import com.lee.pojo.Blog;
import com.lee.pojo.User;
import com.lee.service.BlogService;
import com.lee.service.EsBlogService;
import com.lee.vo.BlogResponseResult;
import com.lee.vo.BlogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "博客相关的接口", tags = {"CRUD的controller"})
@RestController
@RequestMapping("blog")
public class BlogController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    //默认分类id
    private final Long DEFAULT_CATEGORY_ID = 1L;
    @Autowired
    private BlogService blogService;
    @Autowired
    private EsBlogService esBlogService;

    @ApiOperation(value = "分页查询所有博文", notes = "分页查询所有博文的接口")
    @GetMapping("/list")
    public ResponseEntity getAllBlogs(@RequestParam(value = "page", required = false, defaultValue = "1")
                                              Integer page,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "5")
                                              Integer pageSize) {
        PageInfo<BlogVo> blogVos = blogService.queryAllBlogs(page,pageSize);
        return ResponseEntity.ok(new BlogResponseResult(200, blogVos));
    }

    @ApiOperation(value = "保存/修改博客,并同步到elasticSearch", notes = "保存/修改博客的接口,并同步到elasticSearch")
    @PreAuthorize("authentication.name.equals(#username)") //发请求的用户是否当前认证(登录)的用户
    @PostMapping("/{username}/edit")
    public ResponseEntity saveBlog(@PathVariable("username") String username,  //用于@PreAuthorize认证
                                   @RequestBody BlogDto blogDto) {
        if (StringUtils.isBlank(blogDto.getTitle())) {
            return ResponseEntity.badRequest()
                    .body(new BlogResponseResult(400, "博客标题不能为空~"));
        }
        //若用户未选择分类,设置默认分类
        if (blogDto.getCategoryId() == null || blogDto.getCategoryId() <= 0) {
            blogDto.setCategoryId(DEFAULT_CATEGORY_ID);
        }

        blogService.saveBlog(blogDto);
        return ResponseEntity.ok(new BlogResponseResult(200, "保存成功"));
    }

    @ApiOperation(value = "删除博客,并从es集群中删除", notes = "删除博客的接口")
    @PreAuthorize("authentication.name.equals(#username)") //发请求的用户是否当前认证(登录)的用户
    @DeleteMapping
    public ResponseEntity deleteBlog(String username, Long blogId) {
        blogService.deleteBlog(blogId);
        esBlogService.removeByBlogId(blogId);
        return ResponseEntity.ok(new BlogResponseResult(200, "删除成功"));
    }

    @ApiOperation(value = "博客详细页", notes = "获取博客详细信息的接口")
    @PostMapping("/{username}/blogs/{id}")
    public ResponseEntity blogDetail(@PathVariable("username") String username, @PathVariable("id") Long blogId) {
        //查询博客详细
        Blog blog = blogService.queryBlogById(blogId);
        //是否博主本人
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("owner=" + owner);
        return ResponseEntity.ok(new BlogResponseResult(200, new BlogResponseResult(200, blog)));
    }



}
