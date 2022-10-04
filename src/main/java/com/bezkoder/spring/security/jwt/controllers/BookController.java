package com.bezkoder.spring.security.jwt.controllers;

import com.bezkoder.spring.security.jwt.models.Book;
import com.bezkoder.spring.security.jwt.payload.request.BookRequest;
import com.bezkoder.spring.security.jwt.repository.BookRepo;
import com.bezkoder.spring.security.jwt.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    BookRepo bookRepo;

    @GetMapping()
    public ResponseEntity allBook() {
        return ResponseEntity.ok(bookRepo.findAll());
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity create(@RequestBody BookRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
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
    public ResponseEntity edit(@PathVariable(value = "id") int id,
                                      @RequestBody BookRequest request
    ) {
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
    public class CustomUser extends User {
        private final int userID;
        public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired,
                          boolean credentialsNonExpired,
                          boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities, int userID) {
            super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
            this.userID = userID;
        }
    }
}
