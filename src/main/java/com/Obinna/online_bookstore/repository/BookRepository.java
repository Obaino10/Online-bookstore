package com.Obinna.online_bookstore.repository;

import com.Obinna.online_bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {


}
