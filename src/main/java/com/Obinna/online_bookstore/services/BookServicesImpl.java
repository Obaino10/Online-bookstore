package com.Obinna.online_bookstore.services;

import com.Obinna.online_bookstore.dto.requests.BookDto;
import com.Obinna.online_bookstore.entities.Book;
import com.Obinna.online_bookstore.exception.CustomException;
import com.Obinna.online_bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServicesImpl implements BookService {

    private final BookRepository repository;
    private final EntityManager entityManager;


    @Override
    public Book createBook(BookDto dto) {
        Book newBook = Book.builder()
                .title(dto.getTitle().toLowerCase())
                .author(dto.getAuthor())
                .category(dto.getCategory())
//                .createdAt(LocalDateTime.now())
                .price(dto.getPrice())
                .build();
        return repository.save(newBook);
    }


    @Override
    @CacheEvict(cacheNames = "unique_book", key = "#id")
    public void deleteBook(Long id) {
        Optional<Book> book = repository.findById(id);
        if (book.isEmpty()) {
            log.error("Book does not exist");
            throw new CustomException("No book with ID " + id);
        }
        repository.deleteById(book.get().getId());
        log.info("Book with id {} deleted ", id);
    }

    @Override
    @Cacheable(cacheNames = "unique_book", key = "#id")
    public Book getUniqueBook(Long id) {
        Optional<Book> book = repository.findById(id);
        return book.orElseThrow(() -> new CustomException("No book with ID " + id));
    }

    @Override
    public List<Book> searchForBooks(String title, String author, String category) {
      String  bookBuilder = buildSearchQuery(author, category);
        TypedQuery<Book> query = entityManager.createQuery(bookBuilder, Book.class);
        return parameterBuilder(title, author, category, query);
    }

    @Override
    public List<Book> findBooks(Integer pageNumber, Integer pageSize, String title, String author, String category, String sortProperty) {
        String bookBuilder = buildSearchQuery(author, category);
        TypedQuery<Book> query = entityManager.createQuery(bookBuilder, Book.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);
        return parameterBuilder(title, author, category, query);
    }


    private List<Book> parameterBuilder(String title, String author, String category, TypedQuery<Book> query) {
        query.setParameter("title", title);

        if (author != null && !"".equals(author.trim())) {
            query.setParameter("author", author);
        }
        if (category != null && !"".equals(category.trim())) {
            query.setParameter("category", category);
        }
        return query.getResultList();
    }


    private String buildSearchQuery(String author, String category) {
        StringBuilder builder = new StringBuilder()
                .append("select b from Book b where lower(b.title) like CONCAT('%',:title,'%')");

        if (author != null && !author.trim().isEmpty()) {
            builder.append("and lower(b.author) = lower(:author) ");
        }
        if (category != null && !category.trim().isEmpty()) {
            builder.append("and lower(b.category) = lower(:category) ");
        }

       return builder.toString();
    }
}
