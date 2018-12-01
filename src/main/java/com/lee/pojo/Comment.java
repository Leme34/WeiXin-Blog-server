package com.lee.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class Comment {
    @Id
    private Long id;

    private String content;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private Long pid;  //一级子评论和二级子评论的pid都=顶层父评论的id

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "blog_id")
    private Long blogId;

    @Column(name = "reply_user_id")
    private Long replyUserId;  //二级子评论被回复者id,一级子评论为0

    //级联查询的字段,是非数据库映射字段,插入操作时会忽略
    @Transient
    private String userName; //发评论者用户名
    @Transient
    private String userAvatar; //发评论者头像
    @Transient
    private String replyUserName; //被回复者用户名
    @Transient
    private List<Comment> commentList;  //此评论的所有子评论
    @Transient
    private boolean isVoted;  //此用户是否有给此评论点赞
    @Transient
    private String currentUserId;  //用于暂存传入的userId传入子查询中
    @Transient
    private Integer voteNum;  //此评论被点赞总数

}