package com.lee.mapper;

import com.lee.pojo.Comment;
import com.lee.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapperCustom extends MyMapper<Comment> {

    //级联查询此博客所有评论(父子评论是1对多关系)
    List<Comment> queryAllComments(@Param("userId") Long userId,@Param("blogId") Long blogId);

}