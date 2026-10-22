package com.example.video.service.impl;

import com.example.video.entity.Category;
import com.example.video.repository.CategoryRepository;
import com.example.video.repository.VideoRepository;
import com.example.video.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VideoRepository videoRepository;   // 新增，用于删除分类时删除关联视频

    @Override
    public List<Category> listAll() {
        return categoryRepository.findAll(Sort.by("id"));
    }

    @Override
    public Page<Category> listByPage(int pageNo, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        if (keyword == null || keyword.isEmpty()) {
            return categoryRepository.findAll(pageable);
        }
        return categoryRepository.findByNameContaining(keyword, pageable);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 先删除该分类下的所有视频，防止外键约束错误
        videoRepository.deleteByCategoryId(id);
        // 再删除分类本身
        categoryRepository.deleteById(id);
    }
}