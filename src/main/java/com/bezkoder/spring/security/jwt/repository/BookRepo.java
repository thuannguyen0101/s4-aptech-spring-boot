package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
}
