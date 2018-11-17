package com.lee.dto;

import com.lee.pojo.Image;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 数据传输对象
 */
@Data
public class BlogDto {
    private Long id;

    private Integer commentSize;

    private Date createTime;

    private Integer readSize;

    private String summary;

    private String tags;

    private String title;

    private Integer voteSize;

    private Long categoryId;

    private Long userId;

    private List<Image> imageList;

    private String content;

    private String htmlContent;

}
