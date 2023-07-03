package com.Obinna.online_bookstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_Id" , referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "cart",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Book> books = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;


    public void clearCart(){
        books = new ArrayList<>();
    }
}
