package com.vozh.art.bookservice.controller;

import com.vozh.art.bookservice.dto.BookRequst;
import com.vozh.art.bookservice.dto.BookResponse;
import com.vozh.art.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;


    @PostMapping
    public ResponseEntity<BookResponse> placeNewBook(@RequestBody BookRequst bookRequst){

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
}
