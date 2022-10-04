package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.ImportHistoryBook;
import com.bezkoder.spring.security.jwt.models.StoreBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportBookRepo extends JpaRepository<ImportHistoryBook, Long> {
}
