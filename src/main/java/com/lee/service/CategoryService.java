package com.lee.service;

import com.lee.pojo.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 新增该用户的分类
     */
    void saveCategory(Category category);

    /**
     * 查询该用户创建的所有分类
     */
    List<Category> getCategoryByUserId(Long userId);
}
