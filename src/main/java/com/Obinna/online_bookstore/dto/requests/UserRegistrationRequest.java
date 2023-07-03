package com.Obinna.online_bookstore.dto.requests;

import com.Obinna.online_bookstore.enums.Gender;
import com.Obinna.online_bookstore.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
public class UserRegistrationRequest {

    private String name;
    @Pattern(regexp = "^(.+)@(\\S+)$", message = "Enter a valid email address")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password must be minimum of eight characters with at least one letter and one number")
    private String password;
    @Pattern(regexp = "(?:(?:(?:\\+?234(?:\\h1)?|01)\\h*)?(?:\\(\\d{3}\\)|\\d{3})|\\d{4})(?:\\W*\\d{3})?\\W*\\d{4}(?!\\d)",
            message = "Enter a valid phone number")
    private String phoneNumber;
    private Role role;
    private Gender gender;

}
