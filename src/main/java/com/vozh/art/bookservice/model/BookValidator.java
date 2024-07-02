package com.vozh.art.bookservice.model;

import com.vozh.art.bookservice.dto.BookRequst;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class BookValidator {
    public void validateBookRequest(BookRequst request) {
        validateField(request.getAuthor(), "Author", this::isNotBlank);
        validateField(request.getTitle(), "Title", this::isNotBlank);
        validateField(request.getIsbn(), "ISBN", this::isValidIsbn);
        validateField(request.getPublishedDate(), "Published date", this::isValidPublishedDate);
    }

    private void validateField(String value, String fieldName, Predicate<String> validator) {
        if (!validator.test(value)) {
            throw new IllegalArgumentException(fieldName + " is invalid");
        }
    }

    private void validateField(LocalDate value, String fieldName, Predicate<LocalDate> validator) {
        if (value == null || !validator.test(value)) {
            throw new IllegalArgumentException(fieldName + " is invalid");
        }
    }

    private boolean isNotBlank(String value) {
        return !StringUtils.isBlank(value);
    }

    private boolean isValidIsbn(String isbn) {
        if (StringUtils.isBlank(isbn)) return false;
        String regex = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
        return Pattern.matches(regex, isbn);
    }

    private boolean isValidPublishedDate(LocalDate date) {
        return !date.isAfter(LocalDate.now());
    }

}
