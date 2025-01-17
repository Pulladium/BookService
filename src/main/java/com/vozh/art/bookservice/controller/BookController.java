package com.vozh.art.bookservice.controller;


import com.vozh.art.bookservice.dto.BookRequst;
import com.vozh.art.bookservice.dto.BookResponse;
import com.vozh.art.bookservice.repository.BookRepo;
import com.vozh.art.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
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
            return ResponseEntity
                    .status(HttpStatus.CREATED).body(createdBook);
        }
        catch (IllegalArgumentException e) {
            log.warn(e.toString());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
            return ResponseEntity
                    .status(HttpStatus.FOUND).body(bookResponses);
        }
        catch (Exception e){
            log.warn(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable("id") Long id){
        try {
            BookResponse bookResponse = bookService.getById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(bookResponse);
        }
        catch (Exception e){
            log.warn(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable("id") Long id, @RequestBody BookRequst requst){
        log.info("Update request received");

        try{

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookService.bookUpdate(id,requst));

        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookResponse> deleteBook(@PathVariable("id") Long id){
        try {
            BookResponse bookResponse = bookService.deleteById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(bookResponse);
        }catch (Exception e){
            log.warn(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
