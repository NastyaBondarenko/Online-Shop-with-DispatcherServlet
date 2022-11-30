package com.bondarenko.onlineshop.web.controller;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.service.CartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(path = "/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    protected String getAllFromCart(Model model, Principal principal) {
        List<Product> cart = cartService.getCart(principal);
        model.addAttribute("cart", cart);
        return "product_cart";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    protected String addToCart(@RequestParam int id, Principal principal) {
        cartService.addToCart(id, principal);
        return "redirect:/products";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    protected String deleteFromCart(@RequestParam int id, Principal principal) {
        cartService.deleteFromCart(id, principal);
        return "redirect:/cart";
    }
}
