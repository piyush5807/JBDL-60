package com.example.minorproject.controller;

import com.example.minorproject.dto.BookCreateRequest;
import com.example.minorproject.models.Book;
import com.example.minorproject.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("")
    public void createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest){
        bookService.create(bookCreateRequest);
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable("bookId") int id){
        return this.bookService.find(id);
    }
}
