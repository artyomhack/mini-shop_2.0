package com.anton.eshop.service;

import com.anton.eshop.data.Cart;
import com.anton.eshop.data.Item;
import com.anton.eshop.data.User;
import com.anton.eshop.dto.CartDTO;
import com.anton.eshop.dto.mapDTO.ItemMapper;
import com.anton.eshop.dto.mapDTO.ProductMapper;
import com.anton.eshop.repository.CartRepository;
import com.anton.eshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    private final ItemMapper itemMapper = ItemMapper.MAPPER;
    private final ProductMapper productMapper = ProductMapper.MAPPER;

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ItemService itemService;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, UserService userService, ItemService itemService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

//    @Override
//    @Transactional
//    public Cart addItemInCart(String username, Long productId) {
//        User user = userService.fetchUserByUsername(username);
//        if (user.getCart() == null) {
//            Cart cart = new Cart();
//            user.setCart(cart);
//            cart.setUser(user);
//        }
//        Item item = ;
//        Cart cart = user.getCart();
//        cart.getItems().add(item);
//        return cart;
//    }

    @Override
    public CartDTO getCartByUsername(String username) {
        User user = userService.fetchUserByUsername(username);

        if (Objects.isNull(user) || user.getCart() == null) return new CartDTO();

        CartDTO cartDTO = new CartDTO();

        cartDTO.setId(user.getCart().getId());
        cartDTO.aggregate();

        return cartDTO;
    }



    private List<Item> getCollectItemByItemIds(List<Long> productsIds) {
        List<Item> items = new ArrayList<>();
        for (Long id : productsIds) {
            if (productRepository.findById(id).isPresent()) {
                items.add(itemService.toEntity(
                        itemMapper.productDTOMapToItemModel(
                        productMapper.productMapProductDTO(productRepository.findById(id).get()
                        ))
                ));
            }
        }

        return items;
    }
}
