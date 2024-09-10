package com.jaehyune.stamp_app.service.impl;

import com.jaehyune.stamp_app.dto.UserCreationDTO;
import com.jaehyune.stamp_app.dto.UserDTO;
import com.jaehyune.stamp_app.entity.User;
import com.jaehyune.stamp_app.repository.UserRepository;
import com.jaehyune.stamp_app.service.UserService;
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
    public UserDTO updateUser(User user) {
        // TODO: Add username and email validation
        // save the user object to the db then return the user
        userRepository.save(user);
        return toDto(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO findById(Integer id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            return toDto(result.get());
        } else {
            throw new RuntimeException("Did not find User with ID: " + id);
        }
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::toDto).toList();
    }

    @Override
    public UserDTO createUser(UserCreationDTO dto) {
        // TODO: Add username and email validation
        // save the user object to the db then return the user
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        userRepository.save(user);

        return toDto(user);
    }

    @Override
    public User toEntity(UserDTO dto) {
        User user = new User();

        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());

        return user;
    }

    @Override
    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());

        return dto;
    }
}
