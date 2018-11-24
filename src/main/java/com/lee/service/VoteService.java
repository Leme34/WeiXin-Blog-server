package com.lee.service;

import java.util.List;

/**
 * 博客点赞 和 评论点赞 相关服务
 */
public interface VoteService {

    /**
     * 点赞博客
     */
    void voteBlog(Long userId, Long blogId);

    /**
     * 取消点赞博客
     */
    void cancelVoteBlog(Long userId, Long blogId);


    /**
     * 查询此用户是否有为此博客点赞
     */
    boolean isVoteBlog(Long userId, Long blogId);

    /**
     * 点赞评论
     */
    void voteComment(Long userId,Long commentId);

    /**
     * 取消点赞评论
     */
    void cancelVoteComment(Long userId,Long commentId);

}
