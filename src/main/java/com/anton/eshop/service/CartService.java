package com.anton.eshop.service;

import com.anton.eshop.data.Cart;
import com.anton.eshop.data.User;
import com.anton.eshop.dto.CartDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    Cart createCart(User user, List<Long> itemIds);

    void addProduct(Cart cart, List<Long> itemIds);

    CartDTO getCartByUsername(String username);


}
