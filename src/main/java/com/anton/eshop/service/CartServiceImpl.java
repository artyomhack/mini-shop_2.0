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

    @Override
    public Cart createCart(User user, List<Long> productsIds) {
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
            user.setCart(cart);

        }
        cart.setItems(getCollectItemByItemIds(productsIds));
        Cart id = cartRepository.save(cart);
        System.out.println("ID cart:" + id.getId());
        return id;
    }

    @Override
    public void addProduct(Cart cart, List<Long> itemIds) {
        List<Item> items = cart.getItems();
        List<Item> updateItems = items == null ? new ArrayList<>()
                : new ArrayList<>(items);
        updateItems.addAll(getCollectItemByItemIds(itemIds));
        cart.setItems(updateItems);
    }

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
                        itemMapper.productMapToItemModel(
                        productMapper.productMapProductDTO(productRepository.findById(id).get()
                        ))
                ));
            }
        }

        return items;
    }
}
