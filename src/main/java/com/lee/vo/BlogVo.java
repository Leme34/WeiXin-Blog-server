package com.lee.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class BlogVo {
    private Long id;

    private Integer commentSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private Integer readSize;

    private String summary;

    private String tags;

    private String title;

    private Integer voteSize;

    //分类名称
    private String category;
    //博主
    private String userName;
    //头像
    private String avatar;

    private String content;

    private String htmlContent;

    private String imageList;
}