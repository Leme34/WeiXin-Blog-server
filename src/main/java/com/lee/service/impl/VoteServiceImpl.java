package com.lee.service.impl;

import com.lee.mapper.BlogVoteMapper;
import com.lee.mapper.CommentVoteMapper;
import com.lee.pojo.Blog;
import com.lee.pojo.BlogVote;
import com.lee.pojo.CommentVote;
import com.lee.service.BlogService;
import com.lee.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private BlogVoteMapper blogVoteMapper;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentVoteMapper commentVoteMapper;

    @Override
    public void voteBlog(Long userId, Long blogId) {
        //1、插入blog_vote表记录
        BlogVote blogVote = new BlogVote();
        blogVote.setBlogId(blogId);
        blogVote.setUserId(userId);
        blogVoteMapper.insert(blogVote);
        //2、blog表点赞数+1
        Blog blog = blogService.queryBlogById(blogId);
        blog.setVoteSize(blog.getVoteSize() + 1);
        blogService.updateBlog(blog);
    }

    @Override
    public void cancelVoteBlog(Long userId, Long blogId) {
        //1、删除blog_vote表记录
        BlogVote blogVote = new BlogVote();
        blogVote.setBlogId(blogId);
        blogVote.setUserId(userId);
        blogVoteMapper.delete(blogVote);
        //2、blog表点赞数-1
        Blog blog = blogService.queryBlogById(blogId);
        blog.setVoteSize(blog.getVoteSize() - 1);
        blogService.updateBlog(blog);
    }

    @Override
    public boolean isVoteBlog(Long userId, Long blogId) {
        BlogVote blogVote = new BlogVote();
        blogVote.setUserId(userId);
        blogVote.setBlogId(blogId);
        return blogVoteMapper.selectOne(blogVote) == null ? false : true;
    }

    @Override
    public void voteComment(Long userId, Long commentId) {
        CommentVote commentVote = new CommentVote();
        commentVote.setUserId(userId);
        commentVote.setCommentId(commentId);
        commentVoteMapper.insert(commentVote);
    }

    @Override
    public void cancelVoteComment(Long userId, Long commentId) {
        CommentVote commentVote = new CommentVote();
        commentVote.setUserId(userId);
        commentVote.setCommentId(commentId);
        commentVoteMapper.delete(commentVote);
    }
}
