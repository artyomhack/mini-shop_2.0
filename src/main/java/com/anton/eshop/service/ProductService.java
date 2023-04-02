package com.anton.eshop.service;

import com.anton.eshop.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    void create(ProductDTO productDTO);

    List<ProductDTO> fetchAll();

    ProductDTO fetchId(Long id);

    void addUserToCart(String username, Long productId);
}
