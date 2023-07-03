package com.Obinna.online_bookstore.services;

import com.Obinna.online_bookstore.config.security.AuthenticateService;
import com.Obinna.online_bookstore.config.security.JwtService;
import com.Obinna.online_bookstore.dto.responses.LoginResponse;
import com.Obinna.online_bookstore.dto.responses.UserRegistrationResponse;
import com.Obinna.online_bookstore.dto.requests.UserRegistrationRequest;
import com.Obinna.online_bookstore.entities.Book;
import com.Obinna.online_bookstore.entities.Cart;
import com.Obinna.online_bookstore.entities.User;
import com.Obinna.online_bookstore.enums.Role;
import com.Obinna.online_bookstore.exception.CustomException;
import com.Obinna.online_bookstore.repository.UserRepository;
import com.Obinna.online_bookstore.dto.requests.LoginRequest;
import com.Obinna.online_bookstore.repository.BookRepository;
import com.Obinna.online_bookstore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder encoder;
    private final AuthenticateService authenticateService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public UserRegistrationResponse userRegistration(UserRegistrationRequest dto) {
        log.info("Registering new user :::: {}", dto.getName());
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isPresent()) {
            throw new CustomException("User already exist with email " + dto.getEmail());
        }
        User newUser = new User();
        newUser.setName(dto.getName());
        newUser.setEmail(dto.getEmail());
        newUser.setRole(dto.getRole());
        newUser.setGender(dto.getGender());
        newUser.setPassword(encoder.encode(dto.getPassword()));
        newUser.setPhoneNumber(dto.getPhoneNumber());
        newUser.setCreatedAt(LocalDateTime.now());
        userRepository.save(newUser);
        log.info("Successful registration :::: {}", dto.getName());
        return UserRegistrationResponse.convertUser(newUser);
    }


    @Override
    public LoginResponse Login(LoginRequest loginRequest) {
        var user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.get().getRole().equals(Role.USER)) {
            return userLogin(loginRequest);
        } else if (user.get().getRole().equals(Role.ADMIN)) {
            return adminLogin(loginRequest);
        } else
        throw new CustomException("Incorrect username or password");
    }


    @Override
    public void addBookToCart(Long bookId, Long userId, String userEmail) {
       Optional<User> cartOwner = userRepository.findByEmail(userEmail);
        Book bookToAdd = getBook(bookId);
        Cart userCart = cartOwner.get().getCart();
        if (userCart == null) {
            userCart = new Cart();
        }
        userCart.setUser(cartOwner.get());
        userCart.setCreatedAt(LocalDateTime.now());
        userCart.getBooks().add(bookToAdd);
        cartRepository.save(userCart);
        bookToAdd.setCart(userCart);
        bookRepository.save(bookToAdd);
    }


    private Book getBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(()-> new CustomException("No book with ID: "+id));
    }

    private LoginResponse userLogin(LoginRequest loginRequest) {
        return authenticateService.getLoginResponse(loginRequest, authenticationManager, jwtService, Role.USER);
    }

    private LoginResponse adminLogin(LoginRequest loginRequest) {
        return authenticateService.getLoginResponse(loginRequest, authenticationManager, jwtService, Role.ADMIN);
    }


}
