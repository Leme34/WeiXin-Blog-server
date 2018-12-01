package com.lee.service.impl;

import com.lee.pojo.es.EsBlog;
import com.lee.repository.EsBlogRepository;
import com.lee.service.EsBlogService;
import com.lee.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsBlogServiceImpl implements EsBlogService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Override
    public void removeByBlogId(Long blogId) {
        esBlogRepository.deleteByBlogId(blogId);
    }

    @Override
    public Page<EsBlog> searchBlogs(int pageIndex, int pageSize,String key) {
        Pageable pageable = new PageRequest(pageIndex, pageSize);
        return esBlogRepository.findByTitleContainingOrSummaryContainingOrContentContainingOrCategoryContaining(key,key,key,key,pageable);
    }

    @Override
    public EsBlog updateEsBlog(EsBlog esBlog) {
        return esBlogRepository.save(esBlog);
    }

    @Override
    public EsBlog getEsBlogByBlogId(Long blogId) {
        return esBlogRepository.findByBlogId(blogId);
    }
}
