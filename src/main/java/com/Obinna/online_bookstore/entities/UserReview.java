package com.Obinna.online_bookstore.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reviewMessage;
    private String rating;

    @ManyToOne()
    @JoinColumn(name ="book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
