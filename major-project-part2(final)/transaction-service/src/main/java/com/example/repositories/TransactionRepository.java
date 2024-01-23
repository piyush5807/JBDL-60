package com.example.repositories;

import com.example.models.Transaction;
import com.example.models.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findByExternalTransactionId(String externalTxnId);

    @Modifying
    @Transactional // transaction behaviour / atomic execution
    @Query("update Transaction t set t.transactionStatus = ?2 where t.externalTransactionId = ?1")
    void updateTransactionStatusForExtTxnId(String externalTxnId, TransactionStatus transactionStatus);
}
