package com.example.minorproject.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

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

    @ManyToOne // @OneToMany @OneToOne  @ManyToMany
    private Author author; // Book --> Author {M : 1}

    @JoinColumn
    @ManyToOne // JPA relationships [This will not join the table]
    private Student student; // Book --> Student {M: 1}

    @Enumerated(value = EnumType.ORDINAL)
    private Genre genre;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;
}
