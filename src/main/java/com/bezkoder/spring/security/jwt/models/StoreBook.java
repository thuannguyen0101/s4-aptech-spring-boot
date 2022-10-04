package com.bezkoder.spring.security.jwt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_store", uniqueConstraints={
		@UniqueConstraint(columnNames = {"book_id", "store_id"})})
public class StoreBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "book_id")
	private int bookId;
	@Column(name = "store_id")
	private int storeId;
	private int quantity;
	private LocalDateTime createdAt;

}
