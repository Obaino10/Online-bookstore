package com.Obinna.online_bookstore.repository;

import com.Obinna.online_bookstore.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


}
