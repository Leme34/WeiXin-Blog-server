package com.lee.controller;

import com.lee.pojo.Category;
import com.lee.pojo.User;
import com.lee.service.BlogService;
import com.lee.service.CategoryService;
import com.lee.service.UserService;
import com.lee.vo.BlogResponseResult;
import com.lee.vo.BlogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="用户相关的接口", tags= {"UserController"})
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询用户信息
     */
    @ApiOperation(value="查询用户信息", notes="根据用户名查询用户信息")
    @GetMapping("/{username}")
    public ResponseEntity user(@PathVariable("username") String username){
        User user = userService.queryUserByUsername(username);
        return ResponseEntity.ok(new BlogResponseResult(200,user));
    }

    /**
     * 更新用户信息,请求的bean中null的属性不会被修改
     */
    @PreAuthorize("authentication.name.equals(#username)")  //只有本人可以操作
    @ApiOperation(value="更新用户信息", notes="请求的bean中null的属性不会被修改")
    @PostMapping("/{username}")
    public ResponseEntity userBlogs(@PathVariable("username")String username,User user){
//        System.out.println("上传的user="+user);
        //更新当前用户信息
        userService.updateUser(username,user);
        return ResponseEntity.ok(new BlogResponseResult(200,"更新成功~"));
    }

    /**
     * 查出该用户发表的所有博客
     */
    @ApiOperation(value="用户主页博客列表", notes="用户主页博客列表")
    @GetMapping("/{username}/blogs")
    public ResponseEntity userBlogs(@PathVariable("username")String username){
        List<BlogVo> blogVoList = blogService.queryBlogsByUsername(username);
        return ResponseEntity.ok(new BlogResponseResult(200,blogVoList));
    }

    /**
     * 查询该用户创建的所有分类
     */
    @ApiOperation(value="查询该用户创建的所有分类", notes="查询该用户创建的所有分类")
    @GetMapping("/category")
    public ResponseEntity userBlogs(Long userId){
        List<Category> categoryList = categoryService.getCategoryByUserId(userId);
        System.out.println("查询出的categoryList="+categoryList);
        return ResponseEntity.ok(new BlogResponseResult(200,categoryList));
    }


}
