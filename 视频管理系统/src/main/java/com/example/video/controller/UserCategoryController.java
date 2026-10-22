package com.example.video.controller;

import com.example.video.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/category")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("categories", categoryService.listAll());
        return "user/category-list";
    }
}