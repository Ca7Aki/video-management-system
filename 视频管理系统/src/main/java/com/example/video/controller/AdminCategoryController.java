package com.example.video.controller;

import com.example.video.entity.Category;
import com.example.video.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/category")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size,
                       Model model) {
        Page<Category> pageResult = categoryService.listByPage(page, size, null);
        model.addAttribute("page", pageResult);
        return "admin/category-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        return "admin/category-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/admin/category/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin/category/list";
    }
}