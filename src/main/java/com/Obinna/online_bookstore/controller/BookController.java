package com.Obinna.online_bookstore.controller;

import com.Obinna.online_bookstore.dto.requests.BookDto;
import com.Obinna.online_bookstore.entities.Book;
import com.Obinna.online_bookstore.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createNewBook (@RequestBody BookDto bookDto) {
        Book newBook = bookService.createBook(bookDto);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{bookId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getBookByid (@PathVariable Long bookId) {
        Book existing = bookService.getUniqueBook(bookId);
        return new ResponseEntity<>(existing, HttpStatus.OK);
    }

    @GetMapping(value = "/serchBooks", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getBookByid (
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category

    ) {
        List<Book> searchResult = bookService.searchForBooks(title, author, category);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("delete/{bookId}")
    private String deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return "Book successfully deleted";
    }
}
