package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.models.Book;
import com.bezkoder.spring.security.jwt.models.ImportHistoryBook;
import com.bezkoder.spring.security.jwt.models.StoreBook;
import com.bezkoder.spring.security.jwt.payload.request.BookRequest;
import com.bezkoder.spring.security.jwt.repository.BookRepo;
import com.bezkoder.spring.security.jwt.repository.ImportBookRepo;
import com.bezkoder.spring.security.jwt.repository.StoreBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    BookRepo bookRepo;
    @Autowired
    StoreBookRepo storeBookRepo;
    @Autowired
    ImportBookRepo importBookRepo;

    @GetMapping()
    public ResponseEntity allBook(@RequestParam(name = "name", required = false) String name ) {
        if (name != null) {
            return ResponseEntity.ok(bookRepo.findBookByName(name));
        }
        return ResponseEntity.ok(bookRepo.findAll());
    }


    @PostMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity create(@RequestBody BookRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = (((UserDetails) principal).getUsername());
        }

        return ResponseEntity.ok(bookRepo.save(new Book(request)));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable(value = "id") int id) {
        boolean isCheck = false;
        try {
            Book book = bookRepo.findById((long) id).orElseThrow(() -> new RuntimeException("Not found"));
            bookRepo.delete(book);
            isCheck = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(isCheck);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity edit(@PathVariable(value = "id") int id, @RequestBody BookRequest request) {
        boolean isCheck = false;
        try {
            Book book = bookRepo.findById((long) id).orElseThrow(() -> new RuntimeException("Not found"));
            book.setName(request.getName());
            book.setPrice(request.getPrice());
            book.setCategoryId(request.getCategoryId());
            book.setPublisherId(request.getPublisherId());
            book.setDescription(request.getDescription());
            bookRepo.save(book);
            isCheck = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(isCheck);
    }

    @PostMapping("store/import")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity importBook(@RequestBody StoreBook request) {
        StoreBook storeBook = storeBookRepo.save(request);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = (((UserDetails) principal).getUsername());
            if (storeBook != null ){
                ImportHistoryBook importHistoryBook = new ImportHistoryBook();
                importHistoryBook.setStoreBookId(Math.toIntExact(storeBook.getId()));
                importHistoryBook.setQuantity(request.getQuantity());
                importHistoryBook.setCreatedAt(LocalDateTime.now());
                importHistoryBook.setUserName(username);
                importBookRepo.save(importHistoryBook);
            }
        }
        return ResponseEntity.ok(storeBook);
    }
}
