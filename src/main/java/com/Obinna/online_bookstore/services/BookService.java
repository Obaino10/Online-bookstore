package com.Obinna.online_bookstore.services;

import com.Obinna.online_bookstore.entities.Book;
import com.Obinna.online_bookstore.dto.requests.BookDto;

import java.util.List;

public interface BookService {
    Book createBook(BookDto dto);

    void deleteBook(Long id);

    Book getUniqueBook(Long id);

    List<Book> searchForBooks(String title, String author, String category);

    List<Book> findBooks(Integer pageNumber, Integer pageSize, String title, String author, String category, String sortProperty);
}
