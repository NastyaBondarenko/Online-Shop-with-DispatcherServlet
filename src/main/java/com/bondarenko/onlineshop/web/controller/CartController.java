package com.bondarenko.onlineshop.web.controller;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.service.CartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping({"/cart"})
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    protected String getAllFromCart(Model model) {
        List<Product> cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "product_cart";
    }

    //    @PostMapping("/cart/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//    protected String addToCart(@RequestParam int id) {
//        cartService.addToCart(id);
//        return "redirect:/products";
//    }
    @PostMapping("/products/cart/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    protected String addToCart(@RequestParam int id) {
        cartService.addToCart(id);
        return "redirect:/products";
    }

    @PostMapping("/cart/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    protected String deleteFromCart(@RequestParam int id) {
        cartService.deleteFromCart(id);
        return "redirect:/cart";
    }
}
