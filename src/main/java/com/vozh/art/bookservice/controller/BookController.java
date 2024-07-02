package com.vozh.art.bookservice.controller;

import com.vozh.art.bookservice.dto.BookRequst;
import com.vozh.art.bookservice.dto.BookResponse;
import com.vozh.art.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;


    @PostMapping
    public ResponseEntity<BookResponse> placeNewBook(@RequestBody BookRequst bookRequst){
        log.info("Received POST request to /books");
        try {
            BookResponse createdBook = bookService.createNewBook(bookRequst);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> test() {
        log.info("Received GET request to /books");
        return ResponseEntity.ok("Controller is working");
    }
}
