package com.lee.controller;

import com.lee.pojo.es.EsBlog;
import com.lee.service.EsBlogService;
import com.lee.service.SearchService;
import com.lee.vo.BlogResponseResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;
    @Autowired
    private EsBlogService esBlogService;

    @ApiOperation(value = "对此次搜索的key进行缓存排序")
    @PostMapping("/hotKey")
    public ResponseEntity searchRecords(String key) {
        searchService.searchKey(key);
        return ResponseEntity.ok(new BlogResponseResult(200, new BlogResponseResult(200, "缓存搜索词成功~")));
    }

    @ApiOperation(value = "返回前num名热搜索词列表")
    @GetMapping("/hotKey")
    public ResponseEntity getHotSearchRecords(Integer num) {
        List hotSearchRecords = searchService.getHotSearchRecords(num);
        System.out.println("hotSearchRecords=" + hotSearchRecords);
        return ResponseEntity.ok(new BlogResponseResult(200, new BlogResponseResult(200, hotSearchRecords)));
    }

    /**
     * 搜索博客,使用redis缓存
     */
    @ApiOperation(value = "根据关键词搜索博客")
    @GetMapping("/blog")
    public ResponseEntity searchBlogs(@RequestParam(value = "pageIndex", required = false, defaultValue = "0")
                                              int pageIndex,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                              int pageSize,
                                      String key) {
        Page<EsBlog> hotSearchBlogs = esBlogService.searchBlogs(pageIndex, pageSize, key);
        return ResponseEntity.ok(new BlogResponseResult(200, new BlogResponseResult(200, hotSearchBlogs)));
    }


}
