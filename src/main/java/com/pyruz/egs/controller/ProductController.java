package com.pyruz.egs.controller;

import com.pyruz.egs.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/search")
    public ModelAndView searchProduct(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false, defaultValue = "0") Float minPrice,
                                      @RequestParam(required = false, defaultValue = "0") Float maxPrice, @RequestParam(required = false, defaultValue = "0") Short minRate,
                                      @RequestParam(required = false, defaultValue = "5") Short maxRate) {
        ModelAndView modelAndView = new ModelAndView("fragments/products/search-product");
        modelAndView.addObject("productDTOs", productService.searchProduct(title, minPrice, maxPrice, minRate, maxRate).getData());
        return modelAndView;
    }

}
