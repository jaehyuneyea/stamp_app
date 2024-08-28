package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.UserCreationDTO;
import com.jaehyune.stamp_app.dto.UserDTO;
import com.jaehyune.stamp_app.entity.User;

import java.util.List;

/**
 * Service layer for User. Mediates between UserRestController and UserRepository.
 */
public interface UserService extends ConverterMediator<User, UserDTO> {

    /**
     * Create a new User.
     * @param dto UserCreationDTO that is passed in from client side.
     * @return Readable DTO of the user that was just created.
     */
    UserDTO createUser(UserCreationDTO dto);

    /**
     * Update a User. TODO: Needs to be updated to UserDTO instead of User
     * @param dto
     * @return
     */
    UserDTO updateUser(User dto);

    /**
     * Delete a User by their id.
     * @param id id of the User that needs to be deleted.
     */
    void delete(Integer id);

    /**
     * Find a specific User by id.
     * @param id id of the User that needs to be found.
     * @return Readable User DTO of the User that was found.
     */
    UserDTO findById(Integer id);

    /**
     * Find all Users.
     * @return A list DTOs for Users that were found. Return an empty list if else.
     */
    List<UserDTO> findAll();
}
