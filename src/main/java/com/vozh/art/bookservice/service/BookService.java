package com.vozh.art.bookservice.service;

import com.vozh.art.bookservice.dto.BookRequst;
import com.vozh.art.bookservice.dto.BookResponse;
import com.vozh.art.bookservice.exception.BookNotFoundException;
import com.vozh.art.bookservice.model.Book;
import com.vozh.art.bookservice.model.BookValidator;
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
    private final BookValidator requstValidator;

    public BookResponse createNewBook(BookRequst requst){

        requstValidator.validateBookRequest(requst);

        Book newBook =  Book.builder()
                .author(requst.getAuthor())
                .title(requst.getTitle())
                .isbn(requst.getIsbn())
                .publishedDate(requst.getPublishedDate())
                .build();

        log.info("Book has been saved to H2 Db");
        return map2BookResponse(bookRepo.save(newBook));


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
            Book book = book2Response.get();
            return this.map2BookResponse(book);
        }
        else {
            log.warn("no book with id:{} was foud", id);
            return null;
        }
    }

    //Put
    @Transactional
    public BookResponse bookUpdate(Long id, BookRequst requst){
        requstValidator.validateBookRequest(requst);

        Optional<Book> book2Update = bookRepo.findById(id);
        if(book2Update.isPresent()){
            Book book = book2Update.get();

            //book update
            book.setAuthor(requst.getAuthor());
            book.setTitle(requst.getTitle());
            book.setIsbn(requst.getIsbn());
            book.setPublishedDate(requst.getPublishedDate());

            Book updatedBook = bookRepo.save(book);
            return map2BookResponse(updatedBook);
        }
        else {
//            throw new BookNotFoundException("Irrelated id:" + id);
            log.error("Book by id {} not found", id);
            return null;
        }

    }

    @Transactional
    public BookResponse deleteById(Long id){

        Optional<Book> deletedBook = bookRepo.deleteBookById(id);
        if(deletedBook.isEmpty()){
//            throw new BookNotFoundException("Book not found with id: " + id);
            log.warn("Book not found with id: {}", id);
            return null;
        }
        return map2BookResponse(deletedBook.get());

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
