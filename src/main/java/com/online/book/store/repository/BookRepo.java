package com.online.book.store.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.online.book.store.configuration.BookRegistration;

// съхранение на информация за книги
public interface BookRepo extends CrudRepository<BookRegistration, String> {
    List<BookRegistration> findAll();
}
