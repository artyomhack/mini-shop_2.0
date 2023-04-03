package com.anton.eshop.service;

import com.anton.eshop.data.Cart;
import com.anton.eshop.data.User;
import com.anton.eshop.dto.CartDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    void addItemInCart(String username, Long productId);

    CartDTO getCartByUsername(String username);
}
