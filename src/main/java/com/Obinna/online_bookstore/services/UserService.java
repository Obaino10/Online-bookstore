package com.Obinna.online_bookstore.services;

import com.Obinna.online_bookstore.dto.responses.LoginResponse;
import com.Obinna.online_bookstore.dto.responses.UserRegistrationResponse;
import com.Obinna.online_bookstore.dto.requests.UserRegistrationRequest;
import com.Obinna.online_bookstore.dto.requests.LoginRequest;

import javax.transaction.Transactional;

public interface UserService {
    UserRegistrationResponse userRegistration(UserRegistrationRequest dto);



    LoginResponse Login(LoginRequest loginRequest);

    @Transactional
    void addBookToCart(Long bookId, Long userId, String userEmail);
}
