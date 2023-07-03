package com.Obinna.online_bookstore.dto.requests;

import com.Obinna.online_bookstore.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {

    private String title;
    private String author;
    private Category category;
    private Double price;

}
