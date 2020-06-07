package com.example.demo.business.repository;

import com.example.demo.business.model.User;

import java.util.Map;

public interface UserRepository {

    void save(User user);

    Map<String, User> findAll();

    User findById(String id);

    void update(User user);

    void delete(String id);
}
