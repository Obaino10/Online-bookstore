package com.Obinna.online_bookstore.dto.responses;

import com.Obinna.online_bookstore.entities.Book;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CheckoutResponse {

  private List<Book> orderedBooks = new ArrayList<>();
  private double payableAmount;
}
