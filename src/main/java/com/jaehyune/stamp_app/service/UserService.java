package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.UserCreationDTO;
import com.jaehyune.stamp_app.dto.UserDTO;
import com.jaehyune.stamp_app.entity.User;

import java.util.List;

public interface UserService extends ConverterMediator<User, UserDTO> {

    // create or update a user
    UserDTO createUser(UserCreationDTO dto);

    UserDTO updateUser(User dto);

    // delete user by id
    void delete(Integer id);

    // find a specific user by id
    UserDTO findById(Integer id);

    // find all users
    List<UserDTO> findAll();
}
