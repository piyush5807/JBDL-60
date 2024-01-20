package com.example.minorproject.repository;

import com.example.minorproject.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {


    // select * from transaction where s_id = ? and b_id = ? and type = ? and status = ? order by id desc limit 1
    Transaction findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByIdDesc(
            Student student, Book book,
            TransactionType transactionType,
            TransactionStatus transactionStatus);

    Transaction findByExternalTxnId(String id);

}
