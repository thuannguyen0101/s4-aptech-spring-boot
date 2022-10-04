package com.bezkoder.spring.security.jwt.payload.request;

import lombok.Data;

@Data
public class BookRequest {

    private String name;
    private double price;
    private String description;
    private String author;
    private int categoryId;
    private int publisherId;
}
