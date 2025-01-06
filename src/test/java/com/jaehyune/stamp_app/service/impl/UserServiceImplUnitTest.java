package com.jaehyune.stamp_app.service.impl;

import com.jaehyune.stamp_app.dto.UserCreationDTO;
import com.jaehyune.stamp_app.dto.UserDTO;
import com.jaehyune.stamp_app.entity.User;
import com.jaehyune.stamp_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void create_user() {
        UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                .username("testuser")
                .email("test@email.com")
                .password("123")
                .build();
//        UserCreationDTO userCreationDTO = new UserCreationDTO("testuser", "test@email.com", "123");

        User user = new User();
        user.setEmail(userCreationDTO.getEmail());
        user.setUsername(userCreationDTO.getUsername());
        user.setPassword(userCreationDTO.getPassword());

        userService.createUser(userCreationDTO);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void update_user() {
        // given a user already exists
        User user = User.builder()
                .username("testuser")
                .email("test@email.com")
                .password("123")
                .build();
//        User user = new User("testuser", "test@email.com", "123");

        userService.updateUser(user);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void create_user_validation_failed_email() {

    }

    @Test
    void create_user_validation_failed_username() {

    }

    @Test
    void findById_user() {
        Integer user_id = 1;
        User user = User.builder()
                .username("testuser")
                .email("test@email.com")
                .password("123")
                .build();
        given(userRepository.findById(user_id)).willReturn(Optional.of(user));
        userService.findById(user_id);

        verify(userRepository, times(1)).findById(user_id);
    }

    @Test
    void findById_user_not_found() {
        Integer user_id = 1;
        given(userRepository.findById(user_id)).willThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> userService.findById(user_id));
        verify(userRepository, times(1)).findById(user_id);
    }

    @Test
    void findById_user_invalid() {
        Integer user_id = -1;
        assertThrows(RuntimeException.class, () -> userService.findById(user_id));
        verify(userRepository, never()).findById(user_id);
    }

    @Test
    void findAll_user() {
        userService.findAll();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void delete_user() {
        Integer user_id = 1;
        userService.delete(user_id);
        verify(userRepository, times(1)).deleteById(user_id);
    }

    @Test
    void delete_user_invalid() {
        Integer user_id = -1;
        assertThrows(RuntimeException.class , () -> userService.delete(user_id));
        verify(userRepository, never()).deleteById(user_id);
    }
    // TODO: add tests for converters and also validations in the service
}