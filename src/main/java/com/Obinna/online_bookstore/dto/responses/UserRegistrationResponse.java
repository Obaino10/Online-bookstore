package com.Obinna.online_bookstore.dto.responses;

import com.Obinna.online_bookstore.entities.User;
import com.Obinna.online_bookstore.enums.Gender;
import com.Obinna.online_bookstore.enums.Role;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserRegistrationResponse {

    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Role role;
    private Gender gender;
    private String createdDate;


    public static UserRegistrationResponse convertUser(User user) {
        return UserRegistrationResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .gender(user.getGender())
                .createdDate(String.valueOf(user.getCreatedAt()))
                .build();
    }

}
