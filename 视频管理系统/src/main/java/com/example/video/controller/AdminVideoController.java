package com.example.video.controller;

import com.example.video.entity.Video;
import com.example.video.service.CategoryService;
import com.example.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/video")
@PreAuthorize("hasRole('ADMIN')")
public class AdminVideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size,
                       @RequestParam(required = false) Long categoryId,
                       Model model) {
        Page<Video> pageResult = videoService.listByPage(page, size, categoryId);
        model.addAttribute("page", pageResult);
        model.addAttribute("categories", categoryService.listAll());
        model.addAttribute("currentCategoryId", categoryId);
        return "admin/video-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("video", new Video());
        model.addAttribute("categories", categoryService.listAll());
        return "admin/video-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("video", videoService.getById(id));
        model.addAttribute("categories", categoryService.listAll());
        return "admin/video-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Video video) {
        videoService.save(video);
        return "redirect:/admin/video/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        videoService.delete(id);
        return "redirect:/admin/video/list";
    }
}