package com.Obinna.online_bookstore.repository;

import com.Obinna.online_bookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
