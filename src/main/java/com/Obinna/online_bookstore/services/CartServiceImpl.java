package com.Obinna.online_bookstore.services;

import com.Obinna.online_bookstore.entities.Book;
import com.Obinna.online_bookstore.entities.Cart;
import com.Obinna.online_bookstore.entities.Transaction;
import com.Obinna.online_bookstore.entities.User;
import com.Obinna.online_bookstore.exception.CustomException;
import com.Obinna.online_bookstore.repository.TransactionRepository;
import com.Obinna.online_bookstore.dto.responses.CheckoutResponse;
import com.Obinna.online_bookstore.repository.CartRepository;
import com.Obinna.online_bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public CheckoutResponse checkoutCart(String userEmail) {
        log.info("User attempting checkout ::::: {}", userEmail);
        Transaction transaction = new Transaction();
        Optional<User> cartOwner = userRepository.findByEmail(userEmail);
        Cart userCart = cartRepository.findCartByUser(cartOwner.get());
        if (userCart == null) {
            log.warn("An error occurred: user cart is empty");
            throw new CustomException("Your cart is empty add a few books before checkout!");
        }
        List<Book> cartBooks = userCart.getBooks();
        double totalPayableAmount = cartBooks.stream()
                .mapToDouble(Book::getPrice).sum();
        CheckoutResponse response = new CheckoutResponse();
//        for (Book b:cartBooks ) {
//            response.getOrderedBooks().add(b);
//        }
        response.setOrderedBooks(cartBooks);
        response.setPayableAmount(totalPayableAmount);
        transaction.setCart(userCart);
        transaction.setUser(cartOwner.get());
        transaction.setAmount(totalPayableAmount);
        transaction.setMessage("Order successfully made by "+userEmail);
//        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
        userCart.clearCart();
        cartRepository.save(userCart);
        log.info("User successfully checkout :::: {}", userEmail);
        return response;
    }

    @Override
    public Cart getUserCart(String userEmail) {
        Optional<User> cartOwner = userRepository.findByEmail(userEmail);
        Cart userCart = cartRepository.findCartByUser(cartOwner.get());
        if (userCart == null) {
            log.warn("An error occurred: user cart is empty");
            throw new CustomException("Your cart is empty add a few books!");
        }
        return userCart;
    }

}
