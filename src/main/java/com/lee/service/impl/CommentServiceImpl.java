package com.lee.service.impl;

import com.lee.mapper.CommentMapper;
import com.lee.mapper.CommentMapperCustom;
import com.lee.pojo.Comment;
import com.lee.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentMapperCustom commentMapperCustom;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
    }

    @Override
    public List<Comment> queryAllComments(Long userId,Long blogId) {
        List<Comment> commentList = commentMapperCustom.queryAllComments(userId,blogId);
        logger.info("queryAllComments:"+commentList);
        return commentList;
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = new Comment();
        comment.setId(commentId);
        commentMapper.delete(comment);
    }
}
