package com.example.video.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

/**
 * 分页工具类（可选）
 * 因为当前项目已直接用 JPA 的 Page，实际不需要此类。
 * 如果你需要将 List 手动转成 Page 或在特殊场景下使用，可保留。
 */
public class PageUtils {

    /**
     * 将 List 手动分页，返回 Spring Data 的 Page 对象
     *
     * @param list     原始数据列表
     * @param pageable 分页参数（page, size）
     * @param total    总记录数（如果列表已经是全部数据，可传 list.size()）
     * @param <T>      数据类型
     * @return 分页后的 Page
     */
    public static <T> Page<T> listToPage(List<T> list, Pageable pageable, long total) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());

        if (start > list.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, total);
        }

        List<T> content = list.subList(start, end);
        return new PageImpl<>(content, pageable, total);
    }
}