package com.online.book.review.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.online.book.review.model.BookRegistration;

// Интерфейс, който наследява CrudRepository и служи за взаимодействие с базата данни за книги (H2 Database)

// съхранение на информация за книги
public interface BookRepo extends CrudRepository<BookRegistration, String> {
    List<BookRegistration> findAll();
}
