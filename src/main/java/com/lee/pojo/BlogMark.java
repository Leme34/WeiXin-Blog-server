package com.lee.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "blog_mark")
public class BlogMark {
    @Column(name = "blog_id")
    private Long blogId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "create_time")
    private Date createTime;
}