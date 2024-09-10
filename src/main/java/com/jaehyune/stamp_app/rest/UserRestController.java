package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.UserCreationDTO;
import com.jaehyune.stamp_app.dto.UserDTO;
import com.jaehyune.stamp_app.entity.User;
import com.jaehyune.stamp_app.rest.error.IdNotFoundException;
import com.jaehyune.stamp_app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Rest Controller for Users. This handles creating, updating, deleting and reading user entities.
 * It delegates the work to UserService.
 */
@RestController
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public UserDTO findById(@PathVariable Integer id) {
        if (id < 0) {
            // throw the exception to be caught in the exception handler
            throw new IdNotFoundException("Invalid ID: " + id);
        }
        return userService.findById(id);
    }

    @GetMapping("/users")
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @PostMapping("/users")
    public UserDTO addUser(@RequestBody UserCreationDTO user) {
        return userService.createUser(user);
    }

    @PutMapping("/users")
    public UserDTO updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        UserDTO user = userService.findById(id);
        if(id < 0) {
            throw new IdNotFoundException("Invalid ID: " + id);
        }
        userService.delete(id);
    }
}
