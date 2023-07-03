package com.Obinna.online_bookstore.controller;

import com.Obinna.online_bookstore.dto.requests.LoginRequest;
import com.Obinna.online_bookstore.dto.requests.UserRegistrationRequest;
import com.Obinna.online_bookstore.dto.responses.ApiResponse;
import com.Obinna.online_bookstore.dto.responses.LoginResponse;
import com.Obinna.online_bookstore.dto.responses.UserRegistrationResponse;
import com.Obinna.online_bookstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping(value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<?>> userRegistration(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        UserRegistrationResponse response = service.userRegistration(userRegistrationRequest);
        return new ResponseEntity<>(new ApiResponse<>("Successfully registered", response), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = service.Login(loginRequest);
        return new ResponseEntity<>(new ApiResponse<>("Login successful", response),HttpStatus.OK);
    }


    @PostMapping(value = "/{bookId}/{userId}/addToCart", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addToUserCart(@PathVariable Long bookId, @PathVariable Long userId, Principal principal) {
        service.addBookToCart(bookId, userId, principal.getName());
        return new ResponseEntity<>(new ApiResponse<>("Added new book to your cart", null), HttpStatus.CREATED);
    }

}
