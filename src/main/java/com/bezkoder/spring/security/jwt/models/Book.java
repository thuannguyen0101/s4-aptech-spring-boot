package com.bezkoder.spring.security.jwt.models;

import com.bezkoder.spring.security.jwt.payload.request.BookRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int userId;
    private String name;
    private String author;
    private int categoryId;
    private int publisherId;
    private double price;
    private String description;
    private LocalDateTime createdDate;

    public Book(BookRequest request) {
        this.name = request.getName();
        this.author = request.getAuthor();
        this.price = request.getPrice();
        this.description = request.getDescription();
        this.categoryId = request.getCategoryId();
        this.publisherId = request.getPublisherId();
        this.createdDate = LocalDateTime.now();
    }
}
