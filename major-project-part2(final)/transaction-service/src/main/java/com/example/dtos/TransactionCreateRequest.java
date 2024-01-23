package com.example.dtos;

import com.example.models.Transaction;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionCreateRequest {

    @NotBlank
    private String sender;

    @NotBlank
    private String receiver;

    @Positive
    private Long amount;

    private String purpose;

    public Transaction to(){
        return Transaction.builder()
                .sender(this.sender)
                .amount(this.amount)
                .receiver(this.receiver)
                .purpose(this.purpose)
                .build();
    }
}
