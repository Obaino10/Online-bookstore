package com.Obinna.online_bookstore.services;

import com.Obinna.online_bookstore.dto.responses.CheckoutResponse;
import com.Obinna.online_bookstore.entities.Cart;

public interface CartService {
    CheckoutResponse checkoutCart(String userEmail);

    Cart getUserCart(String userEmail);
}
