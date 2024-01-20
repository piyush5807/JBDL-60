package com.example.minorproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // this annotation is mandatory for your ORM
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @JoinColumn
    @ManyToOne // @OneToMany @OneToOne  @ManyToMany
    @JsonIgnoreProperties("bookList")
    private Author author; // Book --> Author {M : 1}

    @JoinColumn // will help in creating student_id foreign key column in book table which will be referencing student table's primary key
    @ManyToOne // JPA relationships [This will not join the table]
    @JsonIgnoreProperties({"bookList", "transactionList"})
    private Student student; // Book --> Student {M: 1}

    @Enumerated(value = EnumType.ORDINAL)
    private Genre genre;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties({"book", "student"})
    private List<Transaction> transactionList;
}
