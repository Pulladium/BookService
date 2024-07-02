package com.vozh.art.bookservice.controller;


import com.vozh.art.bookservice.dto.BookRequst;
import com.vozh.art.bookservice.dto.BookResponse;
import com.vozh.art.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/books")
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
    @GetMapping("/test")
    public String test() {
        return "Controller is working";
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks(){
        log.info("Received GET request to /books");
        try {
            List<BookResponse> bookResponses = bookService.getAllBooks();
            return ResponseEntity.status(HttpStatus.FOUND).body(bookResponses);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
}
