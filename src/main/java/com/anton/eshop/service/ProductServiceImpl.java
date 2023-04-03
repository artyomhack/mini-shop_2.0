package com.anton.eshop.service;

import com.anton.eshop.data.Cart;
import com.anton.eshop.data.Item;
import com.anton.eshop.data.Product;
import com.anton.eshop.data.User;
import com.anton.eshop.dto.ProductDTO;
import com.anton.eshop.dto.mapDTO.ItemMapper;
import com.anton.eshop.dto.mapDTO.ProductMapper;
import com.anton.eshop.dto.mapDTO.UserMapper;
import com.anton.eshop.repository.CartRepository;
import com.anton.eshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper = ProductMapper.MAPPER;
    private final ItemMapper itemMapper = ItemMapper.MAPPER;
    private final UserMapper userMapper = UserMapper.MAPPER;

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    private final UserService userService;
    private final ItemService itemService;
    private final CartService cartService;


    public ProductServiceImpl(ProductRepository productRepository, CartRepository cartRepository, UserService userService, ItemService itemService, CartService cartService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.itemService = itemService;
        this.cartService = cartService;
    }

    @Override
    public List<ProductDTO> fetchAll() {
        return productMapper.productsToProductsDTO(productRepository.findAll());
    }

    @Override
    public ProductDTO fetchId(Long id) {
        ProductDTO productDTO = new ProductDTO();
        for (Product product : productRepository.findAll()) {
            if (product.getId().equals(id)) {
                productDTO = productMapper.productMapProductDTO(product);
                break;
            }
        }

        return productDTO;
    }



    @Override
    public void create(ProductDTO productDTO) {
        if (Objects.nonNull(productDTO)) {
            Product product = Product.builder()
                    .id(productDTO.getId())
                    .title(productDTO.getTitle())
                    .amount(productDTO.getAmount())
                    .price(productDTO.getPrice())
                    .items(new ArrayList<>())
                    .build();
            productRepository.save(product);
        } else {
            throw new RuntimeException("Product is null");
        }
    }


}
