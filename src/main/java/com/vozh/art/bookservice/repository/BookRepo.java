package com.vozh.art.bookservice.repository;

import com.vozh.art.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {
    Optional<Book> deleteBookById(Long id);
}
