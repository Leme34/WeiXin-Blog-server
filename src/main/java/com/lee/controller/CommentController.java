package com.lee.controller;

import com.lee.mapper.CommentMapper;
import com.lee.pojo.Blog;
import com.lee.pojo.Comment;
import com.lee.pojo.User;
import com.lee.service.BlogService;
import com.lee.service.CommentService;
import com.lee.vo.BlogResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "评论相关的接口", tags = {"CommentController"})
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    /**
     * 创建评论
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')") //只有这些角色才能评论
    @ApiOperation(value = "添加此博客的评论", notes = "提交评论")
    @PostMapping
    public ResponseEntity saveComment(Comment comment) {
        System.out.println("上传的comment=" + comment);
        //insert此用户的评论
        commentService.saveComment(comment);
        //博客评论量+1
        Long blogId = comment.getBlogId();
        Blog blog = blogService.queryBlogById(blogId);
        blog.setCommentSize(blog.getCommentSize() + 1);
        blogService.updateBlog(blog);
        return ResponseEntity.ok(new BlogResponseResult(200, "评论成功~"));
    }

    /**
     * 级联查询此博客所有评论,每一条评论的被点赞总数
     * 若传了userId 则还需要返回该用户是否有点赞此评论的标志
     */
    @ApiOperation(value = "获取此博客所有评论",notes = "若传了userId包含该用户是否有点赞此评论的标志")
    @GetMapping
    public ResponseEntity getAllComments(Long blogId,
                                         @RequestParam(value = "userId", required = false, defaultValue = "-1")
                                                 Long userId) { //若没有上传userId(未登录)则传-1,使查出的Comment中isVoted字段为false
//        System.out.println("blogId="+blogId+",userId="+userId);
        List<Comment> commentList = commentService.queryAllComments(userId, blogId);
        return ResponseEntity.ok(new BlogResponseResult(200, commentList));
    }

    /**
     * 删除评论,在数据库端使用外键pid实现级联删除子评论
     */
    @PreAuthorize("authentication.name.equals(#username)")
    @ApiOperation(value = "删除评论",notes = "删除评论")
    @DeleteMapping
    public ResponseEntity deleteComments(Long commentId,String username) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(new BlogResponseResult(200, "删除成功~"));
    }

}
