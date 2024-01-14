package com.example.minorproject.repository;

import com.example.minorproject.models.Book;
import com.example.minorproject.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Modifying
    @Transactional
    @Query("update Book b set b.student = ?2 where b.id = ?1")
    void updateBookAvailability(int bookId, Student student);
}
