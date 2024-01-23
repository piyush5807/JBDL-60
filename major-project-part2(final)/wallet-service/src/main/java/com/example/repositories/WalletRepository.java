package com.example.repositories;

import com.example.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Wallet findByContact(String contact);

//    @Query("update Wallet w set w.balance = w.balance + ?2 where w.contact = ?1")
//    void incrementWalletByContact(String contact, Long amount);
//
//    @Query("update Wallet w set w.balance = w.balance - ?2 where w.contact = ?1")
//    void decrementWalletByContact(String contact, Long amount);

    @Modifying
    @Transactional
    @Query("update Wallet w set w.balance = w.balance + ?2 where w.contact = ?1")
    void updateWalletByContact(String contact, Long amount);

}
