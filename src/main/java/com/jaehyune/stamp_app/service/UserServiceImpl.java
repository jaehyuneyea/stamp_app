package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.entity.User;
import com.jaehyune.stamp_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    // the idea here is that we're using JpaRepository to do the work in the db for us
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository; // dependency injection by Spring
    }

    @Override
    public User save(User user) {
        // save the user object to the db then return the user
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Integer id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Did not find User with ID: " + id);
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
