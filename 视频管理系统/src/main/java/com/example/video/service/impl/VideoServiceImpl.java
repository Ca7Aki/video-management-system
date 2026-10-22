package com.example.video.service.impl;

import com.example.video.entity.Video;
import com.example.video.repository.VideoRepository;
import com.example.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    // 旧方法（管理员用），内部调用新方法，keyword 为 null
    @Override
    public Page<Video> listByPage(int pageNo, int pageSize, Long categoryId) {
        return listByPage(pageNo, pageSize, categoryId, null);
    }

    // 新方法：支持 categoryId 和 keyword 的组合筛选
    @Override
    public Page<Video> listByPage(int pageNo, int pageSize, Long categoryId, String keyword) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        // 如果没有筛选条件，则查全部
        if ((categoryId == null || categoryId == 0) && (keyword == null || keyword.isEmpty())) {
            return videoRepository.findAll(pageable);
        }
        // 否则调用组合查询
        return videoRepository.findByCategoryIdAndTitleContaining(
                categoryId == null || categoryId == 0 ? null : categoryId,
                keyword == null || keyword.isEmpty() ? null : keyword,
                pageable);
    }

    @Override
    public void save(Video video) {
        videoRepository.save(video);
    }

    @Override
    public Video getById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        videoRepository.deleteById(id);
    }
}