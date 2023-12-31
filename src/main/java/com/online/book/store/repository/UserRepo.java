package com.online.book.store.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.online.book.store.configuration.UserRegistration;

public interface UserRepo extends CrudRepository<UserRegistration, String> {
    public UserRegistration findByEmailAndPassword(String email, String password);

    public default UserRegistration findByEmail(String email) {
        return null;
    }

    List<UserRegistration> findAll();
}
