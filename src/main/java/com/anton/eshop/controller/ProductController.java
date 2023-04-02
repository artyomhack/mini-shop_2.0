package com.anton.eshop.controller;

import com.anton.eshop.dto.ProductDTO;
import com.anton.eshop.service.CartService;
import com.anton.eshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String productsList(Model model) {
        List<ProductDTO> list = productService.fetchAll();
        model.addAttribute("products", list);

        return "products";
    }

    @GetMapping("/create")
    public String showCreateProduct(@ModelAttribute("product") ProductDTO productDTO, Model model) {
        return "product_create";
    }

    @PostMapping("/create")
    public String createProduct(ProductDTO productDTO, Model model) {
        if (Objects.nonNull(productDTO)) {
            productService.create(productDTO);
        }
        model.addAttribute("product", productDTO);
        return "redirect:/products";
    }


}
