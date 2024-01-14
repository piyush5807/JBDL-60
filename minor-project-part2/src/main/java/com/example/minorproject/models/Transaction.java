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
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String externalTxnId;

    @JoinColumn
    @ManyToOne
    private Student student; // {Txn --> Student}

    @JoinColumn
    @ManyToOne
    private Book book; // {Txn --> Book}

    private Double fine;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @CreationTimestamp
    private Date createdOn; // issue Txn --> Issuance Date

    @UpdateTimestamp
    private Date updatedOn;


}
