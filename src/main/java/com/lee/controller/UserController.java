package com.lee.controller;

import com.lee.enums.OperationEnum;
import com.lee.pojo.Category;
import com.lee.pojo.Follower;
import com.lee.pojo.User;
import com.lee.service.*;
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
    @Autowired
    private FollowerService followerService;
    @Autowired
    private NotifyService notifyService;

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
    public ResponseEntity updateUser(@PathVariable("username")String username,User user){
        System.out.println("上传的user="+user);
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
     * 新增该用户的分类
     */
    @ApiOperation(value="查询该用户创建的所有分类", notes="查询该用户创建的所有分类")
    @PostMapping("/category")
    public ResponseEntity saveUserCategory(Category category){
        categoryService.saveCategory(category);
        return ResponseEntity.ok(new BlogResponseResult(200,"保存成功~"));
    }

    /**
     * 查询该用户创建的所有分类
     */
    @ApiOperation(value="查询该用户创建的所有分类", notes="查询该用户创建的所有分类")
    @GetMapping("/category")
    public ResponseEntity getUserCategory(Long userId){
        List<Category> categoryList = categoryService.getCategoryByUserId(userId);
        System.out.println("查询出的categoryList="+categoryList);
        return ResponseEntity.ok(new BlogResponseResult(200,categoryList));
    }

    /**
     * 查出该用户发表的所有博客
     */
    @ApiOperation(value="用户主页博客列表", notes="用户主页博客列表")
    @GetMapping("/{username}/markBlogs")
    public ResponseEntity userMarkBlogs(@PathVariable("username")String username){
        List<BlogVo> blogVoList = blogService.queryBlogsByUsername(username);
        return ResponseEntity.ok(new BlogResponseResult(200,blogVoList));
    }


    /**
     * 关注用户
     */
    @ApiOperation(value="关注用户")
    @PostMapping("/follow")
    public ResponseEntity userFollowers(Long userId,Long followerId){
        //插入follow表
        followerService.followerUser(userId, followerId);
        //插入notify表
        notifyService.saveNotify(followerId,userId, OperationEnum.FOLLOW.getOperationId());
        return ResponseEntity.ok(new BlogResponseResult(200,"关注成功~"));
    }

    /**
     * 取消关注用户
     */
    @ApiOperation(value="取消关注用户")
    @DeleteMapping("/followers")
    public ResponseEntity cancelFollower(Long userId,Long followerId){
        followerService.cancelFollowerUser(userId, followerId);
        return ResponseEntity.ok(new BlogResponseResult(200,"取消关注成功~"));
    }


    /**
     * 查询是否已关注此用户
     */
    @ApiOperation(value="查询该用户关注的所有用户")
    @GetMapping("/isFollower")
    public ResponseEntity isFollower(Long userId,Long followerId){
        return ResponseEntity.ok(new BlogResponseResult(200,followerService.isFollower(userId,followerId)));
    }

    /**
     * 查询该用户关注的所有用户
     */
    @ApiOperation(value="查询该用户关注的所有用户")
    @GetMapping("/follow")
    public ResponseEntity userFollow(Long followerId){
        List<Follower> followList = followerService.getFollowList(followerId);
        return ResponseEntity.ok(new BlogResponseResult(200,followList));
    }

    /**
     * 查询该用户的粉丝
     */
    @ApiOperation(value="查询该用户的粉丝")
    @GetMapping("/followers")
    public ResponseEntity userFollowers(Long userId){
        List<Follower> followers = followerService.getFollowersByUserId(userId);
        return ResponseEntity.ok(new BlogResponseResult(200,followers));
    }

}
