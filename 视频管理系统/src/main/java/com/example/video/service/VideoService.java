package com.example.video.service;

import com.example.video.entity.Video;
import org.springframework.data.domain.Page;

public interface VideoService {
    // 原有方法（管理员直接使用）
    Page<Video> listByPage(int pageNo, int pageSize, Long categoryId);

    // 新增方法：支持关键词搜索，供用户端使用
    Page<Video> listByPage(int pageNo, int pageSize, Long categoryId, String keyword);

    void save(Video video);
    Video getById(Long id);
    void delete(Long id);
}