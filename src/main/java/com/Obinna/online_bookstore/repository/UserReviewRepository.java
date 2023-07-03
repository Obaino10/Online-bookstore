package com.Obinna.online_bookstore.repository;

import com.Obinna.online_bookstore.entities.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
}
