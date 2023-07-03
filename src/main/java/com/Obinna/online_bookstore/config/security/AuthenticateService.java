package com.Obinna.online_bookstore.config.security;

import com.Obinna.online_bookstore.dto.responses.LoginResponse;
import com.Obinna.online_bookstore.exception.CustomException;
import com.Obinna.online_bookstore.dto.requests.LoginRequest;
import com.Obinna.online_bookstore.entities.User;
import com.Obinna.online_bookstore.enums.Role;
import com.Obinna.online_bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticateService implements UserDetailsService {

    private final UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<? extends GrantedAuthority> authorities = user.get().getRole().equals(Role.USER) ?
                Role.USER.getGrantedAuthorities() : Role.ADMIN.getGrantedAuthorities();
        return new SecurityUser(email, user.get().getPassword(), authorities);
    }

    public LoginResponse getLoginResponse(LoginRequest loginRequest, AuthenticationManager authenticationManager, JwtService jwtService,
                                          Role userRole
    ) {
        String email = loginRequest.getEmail();
        if (email == null || "".equals(email.trim())) {
            throw new CustomException("Email field cannot be empty");
        }
        email = email.toLowerCase();

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new CustomException("Incorrect username or password");
        }
//        System.out.println(user.getPassword());
//        if (!user.getPassword().equals(loginDto.getPassword())) {
//            throw new CustomException("Incorrect username or password");
//        }
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginRequest.getPassword()));
        if (auth.isAuthenticated()) {

            if (!userRole.equals(user.get().getRole())) {
                throw new CustomException("Incorrect username or password");
            }

            String result = "Bearer " + jwtService.generateToken(new SecurityUser(email, loginRequest.getPassword(), auth.getAuthorities()));

            return LoginResponse.builder()
                    .userId(user.get().getId())
                    .token(result)
                    .name(user.get().getName())
                    .email(user.get().getEmail())
                    .gender(user.get().getGender())
                    .role(user.get().getRole())
                    .phoneNumber(user.get().getPhoneNumber())
                    .build();
        } else {
            throw new CustomException("Authentication failed");
        }
    }

}
