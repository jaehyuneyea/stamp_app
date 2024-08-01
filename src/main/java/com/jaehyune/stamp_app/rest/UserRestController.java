package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.entity.User;
import com.jaehyune.stamp_app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {

    // TODO: Add error handling

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable Integer id) {
        if (id < 0) {
            // throw the exception to be caught in the exception handler
            throw new UserNotFoundException("Invalid ID: " + id);
        }
        return userService.findById(id);
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        user.setId(0);
        return userService.save(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        User user = userService.findById(id);
        if(id < 0) {
            throw new UserNotFoundException("Invalid ID: " + id);
        }
        userService.delete(id);
    }
}
