package com.lee.controller;

import com.lee.dto.BlogDto;
import com.lee.pojo.Blog;
import com.lee.pojo.User;
import com.lee.service.BlogService;
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

@Api(value="博客相关的接口", tags= {"CRUD的controller"})
@RestController
@RequestMapping("blog")
public class BlogController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BlogService blogService;

    @ApiOperation(value="查询所有博文", notes="查询所有博文的接口")
    @GetMapping("/list")
    public ResponseEntity getAllBlogs(){
//        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(principal);
        List<BlogVo> blogVos = blogService.queryAllBlogs();
        return ResponseEntity.ok(new BlogResponseResult(200,blogVos));
    }

    @ApiOperation(value="保存博客", notes="保存博客的接口")
    @PreAuthorize("authentication.name.equals(#username)") //发请求的用户是否当前认证(登录)的用户
    @PostMapping("/{username}/blogs/edit")
    public ResponseEntity saveBlog(@PathVariable("username")String username,  //用于@PreAuthorize认证
                                   @RequestBody BlogDto blogDto){
        System.out.println("小程序上传的对象="+blogDto);
        if (StringUtils.isBlank(blogDto.getTitle())){
            return ResponseEntity.badRequest()
                    .body(new BlogResponseResult(400,"博客标题不能为空"));
        }
        blogService.saveBlog(blogDto);
        return ResponseEntity.ok(new BlogResponseResult(200,"保存成功"));
    }


}
