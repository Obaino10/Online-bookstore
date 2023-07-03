package com.Obinna.online_bookstore.repository;

import com.Obinna.online_bookstore.entities.Cart;
import com.Obinna.online_bookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT e FROM Cart e WHERE e.user = :user")
    Cart findCartByUser(User user);
}
