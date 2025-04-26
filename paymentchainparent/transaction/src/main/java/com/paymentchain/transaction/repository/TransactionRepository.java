package com.paymentchain.transaction.repository;

import com.paymentchain.transaction.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT T FROM Transaction T WHERE T.accountIban = ?1")
    public Transaction getAccount(String ibanAccount);
}
