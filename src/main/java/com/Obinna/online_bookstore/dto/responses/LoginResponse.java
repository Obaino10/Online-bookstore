package com.Obinna.online_bookstore.dto.responses;

import com.Obinna.online_bookstore.enums.Gender;
import com.Obinna.online_bookstore.enums.Role;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

  private Long userId;
  private String name;
  private String email;
  private Role role;
  private Gender gender;
  private String phoneNumber;
  private String token;
}
