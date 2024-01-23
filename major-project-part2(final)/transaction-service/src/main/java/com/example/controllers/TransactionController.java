package com.example.controllers;

import com.example.dtos.TransactionCreateRequest;
import com.example.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction")
    public String transact(@Valid @RequestBody TransactionCreateRequest transactionCreateRequest){
        return this.transactionService.transact(transactionCreateRequest);
    }
}
