package com.example.minorproject.dto;

import com.example.minorproject.models.Author;
import com.example.minorproject.models.Book;
import com.example.minorproject.models.Genre;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookCreateRequest {

    // Diff b/w notnull and notblank

    @NotBlank
    private String bookName;

    @NotNull
    private Genre genre;

    // author related data
    @NotBlank
    private String authorEmail;

    private String authorName;
    private String authorCountry;


    public Book to(){
        return Book.builder()
                .name(this.bookName)
                .genre(this.genre)
                .author(
                        Author.builder()
                                .name(this.authorName)
                                .country(this.authorCountry)
                                .email(this.authorEmail)
                                .build()
                )
                .build();
    }
}
