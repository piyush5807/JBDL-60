package com.example.minorproject.dto;

import com.example.minorproject.models.Transaction;
import com.example.minorproject.models.TransactionType;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TransactionCreateRequest {

    @Positive
    private Integer studentId;

    @Positive
    private Integer bookId;

    @NotNull
    private TransactionType transactionType;

//    public Transaction to(){
//        return Transaction.builder()
//                .s
//                .build();
//    }
}
