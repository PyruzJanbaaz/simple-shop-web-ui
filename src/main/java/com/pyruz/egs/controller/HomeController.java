package com.pyruz.egs.controller;

import com.pyruz.egs.service.CategoryService;
import com.pyruz.egs.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    final ProductService productService;
    final CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("productDTOs", productService.getAll().getData());
        modelAndView.addObject("categoryDTOs", categoryService.getAll().getData());
        return modelAndView;
    }

}
