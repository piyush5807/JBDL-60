package com.example.minorproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // telling hibernate to generate id automatically in an incremental fashion
    private Integer id; // primary key

    // studentService.get(1) --> books

    private String name;

    @Column(unique = true)
    private String email;
    private Integer age;

//    @Column(name = "roll_No")
    @Column(unique = true)
    private String roll_number;

    @Column(unique = true)
    private String mobile;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
    private String cardId;

    @OneToMany(mappedBy = "student") // [this doesn't mean that I am storing bookList in student table. This is for reference in Java object]
    @JsonIgnoreProperties({"student", "transactionList"}) // this is not going to affect any db query or response, this is just to tell the jackson databind mappers to ignore some fields from the response
    private List<Book> bookList; // {Student --> Book}

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties({"student", "book"})
    private List<Transaction> transactionList; // {Student : Txn}

    @JoinColumn
    @OneToOne
    @JsonIgnoreProperties({"student", "admin"})
    private User user;

    // select * from book where student_id = ?
}
