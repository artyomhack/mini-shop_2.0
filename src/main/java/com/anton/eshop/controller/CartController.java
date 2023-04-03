package com.anton.eshop.controller;

import com.anton.eshop.dto.CartDTO;
import com.anton.eshop.dto.UserDTO;
import com.anton.eshop.service.CartService;
import com.anton.eshop.service.ItemService;
import com.anton.eshop.service.ProductService;
import com.anton.eshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collections;
import java.util.Objects;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;
    private final ItemService itemService;


    public CartController(ProductService productService, UserService userService, CartService cartService, ItemService itemService)  {
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
        this.itemService = itemService;
    }

    @GetMapping
    public String aboutCart(Model model, Principal principal) {
        if (Objects.isNull(principal)) {
            CartDTO cartDTO = new CartDTO();
            model.addAttribute("cart", cartDTO);
        }
        else {
            CartDTO cartDTO = cartService.getCartByUsername(principal.getName());
            model.addAttribute("cart", cartDTO);
        }
        return "cart";
    }

    @GetMapping("/{product_id}")
    private String addProductInCart(@PathVariable(name = "product_id") Long product_id,
                                    Principal principal) {
        if (Objects.isNull(principal)) {
            return "redirect:/cart";
        }


        return "redirect:/cart";
    }

//    @GetMapping( "{cart_id}/delete//{item_id}" )
//    public ModelAndView deleteProductFromCart(
//                                        @PathVariable(name = "cart_id") String cart_id,
//                                        @PathVariable(name = "item_id") Long item_id,
//                                        Principal principal, Model model) {
//            if (Objects.nonNull(principal)) {
//                CartDTO cartDTO = cartService.getCartByUsername(principal.getName());
//                itemService.deleteItemByCartIdAndProductId(Long.parseLong(cart_id), item_id);
//                model.addAttribute("cart", cartDTO);
//                return new ModelAndView("redirect:/cart");
//            }
//
//            return new ModelAndView("redirect:/cart");
//    }
}
