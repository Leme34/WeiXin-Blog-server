package com.lee.service;

import com.lee.pojo.Comment;

import java.util.List;

public interface CommentService {

    /**
     * insert评论
     */
    void saveComment(Comment comment);

    /**
     * 级联查询此博客所有评论(父子评论是1对多关系)
     */
    List<Comment> queryAllComments(Long userId,Long blogId);

    /**
     * 删除评论
     */
    void deleteComment(Long commentId);
}
