package com.lee.repository;

import com.lee.pojo.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,String>{  //es文档对象,文档id类型

    /**
     * 根据title,summary,content分页模糊查询
     */
    Page<EsBlog> findByTitleContainingOrSummaryContainingOrContentContainingOrCategoryContaining(
            String title, String summary, String content,String category, Pageable pageable);

    /**
     * 根据 blogId 查询 EsBlog
     */
    EsBlog findByBlogId(Long blogId);

    /**
     * 根据 Blog 的id 查询 EsBlog
     */
    void deleteByBlogId(Long blogId);

}
