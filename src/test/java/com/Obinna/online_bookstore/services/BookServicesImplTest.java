package com.Obinna.online_bookstore.services;

import com.Obinna.online_bookstore.dto.requests.BookDto;
import com.Obinna.online_bookstore.entities.Book;
import com.Obinna.online_bookstore.enums.Category;
import com.Obinna.online_bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServicesImplTest {
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private EntityManager entityManager;

    @BeforeEach
    public void beforeEach() {
        bookService = new BookServicesImpl(bookRepository,entityManager );
    }

    @Test
    void createBook() {
        BookDto bookDto = new BookDto("Science", "Obinna", Category.ART, 2.20);
        bookService.createBook(bookDto);
//        Mockito.when(bookRepository.save(any())).thenReturn()
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void deleteBook() {
    }

    @Test
    void getUniqueBook() {
    }

    @Test
    void searchForBooks() {
    }
}