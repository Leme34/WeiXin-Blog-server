package com.lee.service;

import java.util.List;

public interface SearchService {

    /**
     * 若第一次查询该key则缓存到ZSet中
     * 否则获取排序值并且+1
     */
    void searchKey(String key);

    /**
     * 查询redis中的前num名热搜词
     */
    List getHotSearchRecords(Integer num);

}
