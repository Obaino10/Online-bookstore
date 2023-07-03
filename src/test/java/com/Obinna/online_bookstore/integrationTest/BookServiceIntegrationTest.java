package com.Obinna.online_bookstore.integrationTest;

import com.Obinna.online_bookstore.config.security.AuthenticateService;
import com.Obinna.online_bookstore.config.security.JwtService;
import com.Obinna.online_bookstore.controller.BookController;
import com.Obinna.online_bookstore.dto.requests.LoginRequest;
import com.Obinna.online_bookstore.dto.requests.UserRegistrationRequest;
import com.Obinna.online_bookstore.dto.responses.LoginResponse;
import com.Obinna.online_bookstore.dto.responses.UserRegistrationResponse;
import com.Obinna.online_bookstore.enums.Gender;
import com.Obinna.online_bookstore.enums.Role;
import com.Obinna.online_bookstore.repository.BookRepository;
import com.Obinna.online_bookstore.repository.CartRepository;
import com.Obinna.online_bookstore.repository.TransactionRepository;
import com.Obinna.online_bookstore.repository.UserRepository;
import com.Obinna.online_bookstore.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookServiceIntegrationTest {
    private UserService userService;
    private BookService bookService;
    private CartService cartService;
    private TransactionService transactionService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private AuthenticateService authenticateService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;


    @BeforeEach
    public void beforeEach() {
        userService = new UserServiceImpl(userRepository, cartRepository,bookRepository,encoder,authenticateService,authenticationManager,jwtService);
        cartService = new CartServiceImpl(cartRepository, userRepository, transactionRepository);
        bookService = new BookServicesImpl(bookRepository, entityManager);
        transactionService = new TransactionServiceImpl(transactionRepository);
    }

    public void userCanSignUpAddBookToCartAndCheckout() {
        UserRegistrationRequest request = new UserRegistrationRequest("Obinna", "chineduobinna@gmail.com","Obinna11B","+2347087567864",
                Role.USER, Gender.MALE);
        UserRegistrationResponse userRegistrationResponse = userService.userRegistration(request);
        assertThat(userRegistrationResponse.getName()).isEqualTo(request.getName());

        LoginRequest loginRequest = new LoginRequest("chineduobinna@gmail.com","Obinna11B");
        LoginResponse login = userService.Login(loginRequest);

    }
}
