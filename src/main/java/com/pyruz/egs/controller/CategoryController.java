package com.pyruz.egs.controller;

import com.pyruz.egs.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class CategoryController {

    final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ModelAndView getAllCategory() {
        ModelAndView modelAndView = new ModelAndView("category");
        modelAndView.addObject("result", categoryService.getAll().getData());
        return modelAndView;
    }
}
