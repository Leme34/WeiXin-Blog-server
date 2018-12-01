package com.lee.controller;

import com.lee.service.BlogService;
import com.lee.service.MarkService;
import com.lee.vo.BlogResponseResult;
import com.lee.vo.BlogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "收藏相关的接口", tags = {"MarkController"})
@RestController
@RequestMapping("mark")
public class MarkController {

    @Autowired
    private MarkService markService;
    @Autowired
    private BlogService blogService;

    /**
     * 收藏博客
     */
    @PreAuthorize("authentication.name.equals(#username)")
    @ApiOperation(value = "收藏博客")
    @PostMapping("/blog")
    public ResponseEntity voteBlog(Long userId, Long blogId, String username) {
        markService.markBlog(userId, blogId);
        blogService.increaseMarkSize(blogId);
        return ResponseEntity.ok(new BlogResponseResult(200, "收藏成功"));
    }


    /**
     * 取消收藏博客
     */
    @PreAuthorize("authentication.name.equals(#username)")
    @ApiOperation(value = "取消收藏博客")
    @DeleteMapping("/blog")
    public ResponseEntity cancelVoteBlog(Long userId, Long blogId, String username) {
        markService.cancelMarkBlog(userId, blogId);
        blogService.decreaseMarkSize(blogId);
        return ResponseEntity.ok(new BlogResponseResult(200, "取消收藏成功"));
    }

    /**
     * 查询此用户是否有收藏此博客
     */
    @ApiOperation(value = "查询此用户是否有收藏此博客")
    @GetMapping("/blog")
    public ResponseEntity queryIsVoteBlog(Long userId, Long blogId) {
        return ResponseEntity.ok(new BlogResponseResult(200, markService.isMarkBlog(userId, blogId)));
    }

    @ApiOperation(value = "查询用户收藏的博客", notes = "查询用户收藏的博客的接口")
    @GetMapping("/{userId}")
    public ResponseEntity markBlogs(@PathVariable("userId") Long userId) {
        List<BlogVo> markBlogs = blogService.getMarkBlogByUserId(userId);
        return ResponseEntity.ok(new BlogResponseResult(200, new BlogResponseResult(200,markBlogs)));
    }
}
