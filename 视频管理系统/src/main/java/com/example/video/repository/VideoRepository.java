package com.example.video.repository;

import com.example.video.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VideoRepository extends JpaRepository<Video, Long> {
    // 原来的方法（未使用，但保留兼容）
    Page<Video> findByTitleContaining(String title, Pageable pageable);
    Page<Video> findByCategoryId(Long categoryId, Pageable pageable);

    // 新增：组合查询（分类 + 标题关键词）
    @Query("SELECT v FROM Video v WHERE " +
            "(:categoryId IS NULL OR v.categoryId = :categoryId) AND " +
            "(:keyword IS NULL OR v.title LIKE %:keyword%)")
    Page<Video> findByCategoryIdAndTitleContaining(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword,
            Pageable pageable);

    // 新增：按分类ID删除所有相关视频
    void deleteByCategoryId(Long categoryId);
}