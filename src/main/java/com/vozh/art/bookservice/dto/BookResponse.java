package com.vozh.art.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    Long id;
    String title;
    String author;
    String isbn;
    LocalDate publishedDate;
}
