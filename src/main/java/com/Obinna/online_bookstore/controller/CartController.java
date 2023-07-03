package com.Obinna.online_bookstore.controller;

import com.Obinna.online_bookstore.dto.responses.CheckoutResponse;
import com.Obinna.online_bookstore.entities.Cart;
import com.Obinna.online_bookstore.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping(value = "/user_cart", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserCart(Principal principal) {
       Cart userCart = cartService.getUserCart(principal.getName());
       return new ResponseEntity<>(userCart, HttpStatus.OK);
    }

    @PostMapping(value = "checkout", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> cartCheckOut(Principal principal) {
       CheckoutResponse response = cartService.checkoutCart(principal.getName());
       return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
