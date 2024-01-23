package com.example.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String externalTransactionId; // UUID

    private Long amount;

    @Column(nullable = false)
    private String sender; // contact

    @Column(nullable = false)
    private String receiver; // contact

    private String purpose;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updateOn;

}
