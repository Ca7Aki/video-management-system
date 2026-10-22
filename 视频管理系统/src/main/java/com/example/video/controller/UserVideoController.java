package com.example.video.controller;

import com.example.video.service.CategoryService;
import com.example.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/video")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserVideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size,
                       @RequestParam(required = false) Long categoryId,
                       @RequestParam(required = false, defaultValue = "") String keyword,
                       Model model) {
        model.addAttribute("page", videoService.listByPage(page, size, categoryId, keyword));
        model.addAttribute("categories", categoryService.listAll());
        model.addAttribute("currentCategoryId", categoryId);
        model.addAttribute("keyword", keyword);
        return "user/video-list";
    }
}