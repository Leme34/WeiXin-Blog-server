package com.lee.controller;

import com.lee.service.VoteService;
import com.lee.vo.BlogResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "点赞相关的接口", tags = {"VoteController"})
@RestController
@RequestMapping("vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    /**
     * 点赞博客
     */
    @PreAuthorize("authentication.name.equals(#username)")
    @ApiOperation(value = "点赞博客")
    @PostMapping("/blog")
    public ResponseEntity voteBlog(Long userId, Long blogId, String username) {
        voteService.voteBlog(userId, blogId);
        return ResponseEntity.ok(new BlogResponseResult(200, "点赞成功"));
    }

    /**
     * 取消点赞博客
     */
    @PreAuthorize("authentication.name.equals(#username)")
    @ApiOperation(value = "取消点赞博客")
    @DeleteMapping("/blog")
    public ResponseEntity cancelVoteBlog(Long userId, Long blogId, String username) {
        voteService.cancelVoteBlog(userId, blogId);
        return ResponseEntity.ok(new BlogResponseResult(200, "取消点赞成功"));
    }

    /**
     * 查询此用户是否有为此博客点赞
     */
    @ApiOperation(value = "查询此用户是否有为此博客点赞")
    @GetMapping("/blog")
    public ResponseEntity queryIsVoteBlog(Long userId, Long blogId) {
        //已点赞
        if (voteService.isVoteBlog(userId, blogId)) {
            return ResponseEntity.ok(new BlogResponseResult(200, true));
        }
        return ResponseEntity.ok(new BlogResponseResult(200, false));
    }

    /**
     * 点赞评论
     */
    @PreAuthorize("authentication.name.equals(#username)")
    @ApiOperation(value = "点赞评论")
    @PostMapping("/comment")
    public ResponseEntity VoteComments(Long userId, Long commentId, String username) {
        voteService.voteComment(userId, commentId);
        return ResponseEntity.ok(new BlogResponseResult(200, "点赞成功~"));
    }

    /**
     * 取消点赞评论
     */
    @PreAuthorize("authentication.name.equals(#username)")
    @ApiOperation(value = "取消点赞评论")
    @DeleteMapping("/comment")
    public ResponseEntity deleteVoteComments(Long userId, Long commentId, String username) {
        voteService.cancelVoteComment(userId, commentId);
        return ResponseEntity.ok(new BlogResponseResult(200, "取消点赞成功~"));
    }

}
