/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.transaction.controller;

import com.paymentchain.transaction.entities.Status;
import com.paymentchain.transaction.entities.Transaction;
import com.paymentchain.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

/**
 *
 * @author juaoroga
 */
@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @GetMapping("/get-for-number")
    public Transaction getAccount(@RequestParam String accountIban) {
        return transactionRepository.getAccount(accountIban);
    }

    @PostMapping("/create-transaction")
    public ResponseEntity<?> create(@RequestBody Transaction transaction) {
        Date date = new Date();

        if (transaction.getAmount() < 0) {
            return ResponseEntity.badRequest().body("El monto de la transacción no puede ser negativo");
        }

        if (transaction.getFee() > 0) {
            double rest = transaction.getAmount() - transaction.getFee();
            log.info("Comision mayor a 0, su saldo total es de: " + rest);
            transaction.setAmount(rest);
        }

        if (transaction.getDate().compareTo(date) > 0) {
            transaction.setStatus(Status.PENDIENTE);
        } else {
            transaction.setStatus(Status.LIQUIDADA);
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        return ResponseEntity.ok().body(savedTransaction);
    }

    @PutMapping("/update-transaction/{id}")
    public ResponseEntity<?> update(@RequestParam Long id, @RequestBody Transaction transaction) {
        Date date = new Date();

        Optional<Transaction> oldTransaction = transactionRepository.findById(id);
        Transaction old = oldTransaction.get();

        if (oldTransaction.isPresent()) {
            Transaction newTransaction = new Transaction();
            newTransaction.setId(transaction.getId());
            newTransaction.setAccountIban(transaction.getAccountIban());
            newTransaction.setChannel(transaction.getChannel());
            newTransaction.setDate(transaction.getDate());
            newTransaction.setStatus(transaction.getStatus());

            if (transaction.getAmount() > 0) {
                double newAmount = transaction.getAmount() + old.getAmount();
                newTransaction.setAmount(newAmount);
            } else {
                double newAmount = transaction.getAmount() - old.getAmount();
                newTransaction.setAmount(newAmount);
            }
            Transaction savedTransaction = transactionRepository.save(newTransaction);
            return ResponseEntity.ok().body(savedTransaction);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        
    }

}
