package com.online.book.review.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.online.book.review.model.BookRegistration;

// Интерфейс, който наследява CrudRepository и служи за взаимодействие с базата данни за книги (H2 Database)

// съхранение на информация за книги
// Този интерфейс предоставя основните операции за управление на данни (CRUD - Create, Read, Update, Delete) върху обекти от тип BookRegistration
public interface BookRepo extends CrudRepository<BookRegistration, String> {
    List<BookRegistration> findAll();
}
