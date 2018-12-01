package com.lee.service;

public interface MarkService {

    /**
     * 收藏博客
     */
    void markBlog(Long userId, Long blogId);

    /**
     * 取消收藏博客
     */
    void cancelMarkBlog(Long userId, Long blogId);


    /**
     * 查询此用户是否有收藏此博客
     */
    boolean isMarkBlog(Long userId, Long blogId);

}
