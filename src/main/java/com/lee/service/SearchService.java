package com.lee.service;

import com.lee.vo.BlogVo;

import java.util.List;
import java.util.Set;

public interface SearchService {

    /**
     * 若第一次查询该key则缓存到ZSet中
     * 否则获取排序值并且+1
     */
    void searchKey(String key);

    /**
     * 查询redis中的前num名热搜词
     */
    Set getHotSearchRecords(Integer num);

}
