package com.lee.service.impl;

import com.lee.mapper.CategoryMapper;
import com.lee.pojo.Category;
import com.lee.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void saveCategory(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public List<Category> getCategoryByUserId(Long userId) {
        Category category = new Category();
        category.setUserId(userId);
        return categoryMapper.select(category);
    }
}
