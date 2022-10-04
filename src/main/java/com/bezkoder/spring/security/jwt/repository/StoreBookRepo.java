package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.Book;
import com.bezkoder.spring.security.jwt.models.StoreBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreBookRepo extends JpaRepository<StoreBook, Long> {
}
