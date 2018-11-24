package com.lee.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "blog_vote")
public class BlogVote {
    @Column(name = "blog_id")
    private Long blogId;

    @Column(name = "user_id")
    private Long userId;

}