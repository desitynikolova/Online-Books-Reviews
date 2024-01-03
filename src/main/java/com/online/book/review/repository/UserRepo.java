package com.online.book.review.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.online.book.review.model.UserRegistration;

// Интерфейс, който наследява CrudRepository и служи за взаимодействие с базата данни за потребители (H2 Database)

// съхранение на регистрирани потребители
public interface UserRepo extends CrudRepository<UserRegistration, String> {
    public UserRegistration findByEmailAndPassword(String email, String password);

    List<UserRegistration> findAll();
}
