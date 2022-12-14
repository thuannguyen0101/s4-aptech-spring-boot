package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.name like %:name%")
    List<Book> findBookByName(@Param("name") String name);

}
