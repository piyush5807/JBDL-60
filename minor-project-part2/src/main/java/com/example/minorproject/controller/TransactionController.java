package com.example.minorproject.controller;

import com.example.minorproject.dto.TransactionCreateRequest;
import com.example.minorproject.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    /**
     * bookId -
     * studentId -
     */

    @Autowired
    TransactionService transactionService;

    @PostMapping("")
    public String transact(@Valid @RequestBody TransactionCreateRequest transactionCreateRequest) throws Exception {
        return this.transactionService.transact(transactionCreateRequest);
    }
}
