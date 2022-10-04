package com.bezkoder.spring.security.jwt.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "import_history_book", uniqueConstraints={
//        @UniqueConstraint(columnNames = {"book_id", "store_id"})})
public class ImportHistoryBook {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int userId;
    private int quantity;
    private int storeBookId;
    private LocalDateTime createdAt;
}
