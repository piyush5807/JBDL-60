package com.example.minorproject.services;

import com.example.minorproject.dto.BookCreateRequest;
import com.example.minorproject.models.Author;
import com.example.minorproject.models.Book;
import com.example.minorproject.models.Student;
import com.example.minorproject.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;

    public void create(BookCreateRequest bookCreateRequest){

        Book book = bookCreateRequest.to();
        Author author = book.getAuthor();

        author = this.authorService.createOrGet(author);
//        book.setAuthor(author); // for latest spring boot versions, you can emit

        bookRepository.save(book);
    }

    public Book find(int bookId) {
        return this.bookRepository.findById(bookId).orElse(null);
    }

    public void updateBookAvailability(int bookId, Student student) {
        this.bookRepository.updateBookAvailability(bookId, student);
    }
}
