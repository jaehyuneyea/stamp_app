package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.entity.User;

import java.util.List;

public interface UserService {

    // create or update a user
    User save(User user);

    // delete user by id
    void delete(Integer id);

    // find a specific user by id
    User findById(Integer id);

    // find all users
    List<User> findAll();
}
