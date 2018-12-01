package com.lee.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "follower")
public class Follower {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "follower_id")
    private Long followerId;

    //关联查询字段
    @Transient
    private String avatar; //被关注人头像
    @Transient
    private String username; //被关注人用户名
    @Transient
    private String follower;  //关注人用户名
}