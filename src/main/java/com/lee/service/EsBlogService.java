package com.lee.service;

import com.lee.pojo.es.EsBlog;
import com.lee.vo.BlogVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * EsBlog 服务接口.
 */
public interface EsBlogService {

    /**
     * 添加/更新 EsBlog
     */
    EsBlog updateEsBlog(EsBlog esBlog);

    /**
     * 根据Blog的Id获取EsBlog
     */
    EsBlog getEsBlogByBlogId(Long blogId);


    /**
     * 根据id从es集群删除
     */
    void removeByBlogId(Long blogId);

    /**
     * 根据关键词搜索博客
     */
    Page<EsBlog> searchBlogs(int pageIndex, int pageSize, String key);




}
