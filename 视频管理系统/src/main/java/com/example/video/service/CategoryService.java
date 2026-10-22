package com.example.video.service;

import com.example.video.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<Category> listAll();
    Page<Category> listByPage(int pageNo, int pageSize, String keyword);
    void save(Category category);
    Category getById(Long id);
    void delete(Long id);
}