package com.example.minorproject.repository;

import com.example.minorproject.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    /**
     * Hibernate is an ORM which does the mapping b/w Java class and SQL relation / table
     * JPQL - Java Persistence Query Language (Format which allows you to write query keeping java objects into consideration)
     * Native - (Format which allows you write query keeping sql table into consideration)
     */

    @Query(value = "select a from Author a where a.email = :email")
//    @Query("select b from Book b where b.email = ?1")
//    Native query - @Query("select * from book b where b.email = :email", nativeQuery=true)
    Author getByEmail(String email);

    Author findByEmail(String email);

    Author findByEmailAndName(String email, String name); // Function name Nomenclature for JPQL
}
