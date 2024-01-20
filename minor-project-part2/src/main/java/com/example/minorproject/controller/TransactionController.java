package com.example.minorproject.controller;

import com.example.minorproject.dto.TransactionCreateRequest;
import com.example.minorproject.models.Transaction;
import com.example.minorproject.models.User;
import com.example.minorproject.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.transactionService.transact(user.getStudent().getId(), transactionCreateRequest);
    }

    @GetMapping("/{extTxnId}")
    public Transaction getTxnByExternalTxnId(@PathVariable("extTxnId") String extTxnId){
        return this.transactionService.getTxnById(extTxnId);
    }
}
