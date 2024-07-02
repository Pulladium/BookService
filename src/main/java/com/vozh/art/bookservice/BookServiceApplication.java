package com.vozh.art.bookservice;

import com.vozh.art.bookservice.model.Book;
import com.vozh.art.bookservice.repository.BookRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

@SpringBootApplication
public class BookServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner createData(BookRepo bookRepo){
        return args -> {
            Book book1 = Book.builder()
                    .title("BlaBlaBla")
                    .author("VanderHuj")
                    .isbn("7780743273565")
                    .publishedDate(LocalDate.parse("1222-03-23"))
                    .build();

            Book book2 = Book.builder()
                    .title("NONusuasdf")
                    .author("Posdf")
                    .isbn("2280743273565")
                    .publishedDate(LocalDate.parse("2000-03-23"))
                    .build();

            bookRepo.save(book1);
            bookRepo.save(book2);
        };
    }

}
