package com.vozh.art.bookservice.service;

import com.vozh.art.bookservice.dto.BookRequst;
import com.vozh.art.bookservice.dto.BookResponse;
import com.vozh.art.bookservice.model.Book;
import com.vozh.art.bookservice.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepo bookRepo;

    public void createNewBook(BookRequst requst){

        Book newBook =  Book.builder()
                .author(requst.getAuthor())
                .title(requst.getTitle())
                .isbn(requst.getIsbn())
                .publishedDate(requst.getPublishedDate())
                .build();

        bookRepo.save(newBook);
        log.info("Book has been saved to H2 Db");
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks(){
        //TODO sort by id>?
        List<Book> bookList = bookRepo.findAll();

        return bookList.stream().map(this::map2BookResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookResponse getById(Long id){
        Optional<Book> book2Response = bookRepo.findById(id);

        if(book2Response.isPresent()){
//            return (BookResponse) book2Response.stream().map(this::map2BookResponse);
            return bookRepo.findById(id).map(this::map2BookResponse)
                    .orElse(null);
        }else {
            log.warn("no book with id:{} was foud", id);
            return null;
        }
    }

    //Put
    public BookResponse bookUpdate(Long id, BookRequst requst){
        Book newBook =  Book.builder()
                .author(requst.getAuthor())
                .title(requst.getTitle())
                .isbn(requst.getIsbn())
                .publishedDate(requst.getPublishedDate())
                .build();

        bookRepo.save(newBook);

        return getById(id);

    }


    private BookResponse map2BookResponse(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publishedDate(book.getPublishedDate())
                .build();
    }
}
