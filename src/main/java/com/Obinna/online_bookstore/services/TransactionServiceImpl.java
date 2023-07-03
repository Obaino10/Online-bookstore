package com.Obinna.online_bookstore.services;


import com.Obinna.online_bookstore.entities.Transaction;
import com.Obinna.online_bookstore.exception.CustomException;
import com.Obinna.online_bookstore.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService{

  private final TransactionRepository repository;

  @Override
  public Transaction getgtransaction(Long id) {
    Optional<Transaction> transaction = repository.findById(id);
    return transaction.orElseThrow( ()-> new CustomException("No transaction with ID: "+id));
  }

}
