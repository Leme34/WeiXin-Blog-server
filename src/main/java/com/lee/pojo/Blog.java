package com.lee.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Blog {
    @Id
    private Long id;

    @Column(name = "blog_file_id")
    private String blogFileId;

    @Column(name = "comment_size")
    private Integer commentSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "read_size")
    private Integer readSize;

    private String summary;

    private String tags;

    private String title;

    @Column(name = "vote_size")
    private Integer voteSize;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "image_list")
    private String imageList;

    private String content;

    @Column(name = "html_content")
    private String htmlContent;

}