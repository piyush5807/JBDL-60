package com.example.minorproject.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // this annotation is mandatory for your ORM
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String country;

    @CreationTimestamp
    private Date createdOn;

    @OneToMany(mappedBy = "author") // Mapped by will contain the attribute name corresponding to author object in the book class
    private List<Book> bookList; // Author : Book {1: M}

}
