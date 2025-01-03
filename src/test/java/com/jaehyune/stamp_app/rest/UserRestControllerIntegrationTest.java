package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.dto.UserCreationDTO;
import com.jaehyune.stamp_app.dto.UserDTO;
import com.jaehyune.stamp_app.entity.User;
import com.jaehyune.stamp_app.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * An integration test for UserRestController.
 * TODO: Add more custom exceptions and testing
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestExecutionListeners({DirtiesContextTestExecutionListener.class})
class UserRestControllerIntegrationTest {

    private UserService userService;

    private UserDTO userDTO;

    private UserCreationDTO userCreationDTO;

    @Autowired
    public UserRestControllerIntegrationTest(UserService userService) {
        this.userService = userService;
    }

    @BeforeEach
    public void setup() {
        userCreationDTO = UserCreationDTO.builder().build();
        userCreationDTO.setUsername("testuser");
        userCreationDTO.setEmail("test@email.com");
        userCreationDTO.setPassword("test123");
    }

    @Test
    void add_user_then_find_by_id() {
        UserDTO user = userService.createUser(userCreationDTO);

        Assertions.assertEquals(1, user.getId());

        UserDTO foundUser = userService.findById(user.getId());
        Assertions.assertEquals("testuser", foundUser.getUsername());
        Assertions.assertEquals("test@email.com", foundUser.getEmail());
    }

    @Test
    void add_user_then_update() {
        UserDTO user = userService.createUser(userCreationDTO);

        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals("testuser", user.getUsername());
        Assertions.assertEquals("test@email.com", user.getEmail());

        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setPassword("test123");
        newUser.setEmail("different@email.com");
        newUser.setUsername("nottest123");

        UserDTO updatedUser = userService.updateUser(newUser);
        Assertions.assertEquals(1, updatedUser.getId());
        Assertions.assertEquals("different@email.com", newUser.getEmail());
        Assertions.assertEquals("nottest123", newUser.getUsername());
    }

    @Test
    void add_three_users_then_find_all() {
        UserDTO user1 = userService.createUser(userCreationDTO);
        UserDTO user2 = userService.createUser(userCreationDTO);
        UserDTO user3 = userService.createUser(userCreationDTO);

        Assertions.assertEquals(1, user1.getId());
        Assertions.assertEquals(2, user2.getId());
        Assertions.assertEquals(3, user3.getId());

        List<UserDTO> userDTOs = userService.findAll();
        Assertions.assertEquals(3, userDTOs.size());

        for(UserDTO dto: userDTOs) {
            Assertions.assertEquals("test@email.com", dto.getEmail());
            Assertions.assertEquals("testuser", dto.getUsername());
        }
    }

    @Test
    void add_user_then_delete() throws RuntimeException {
        UserDTO user = userService.createUser(userCreationDTO);

        Assertions.assertEquals(1, user.getId());

        userService.delete(user.getId());
        Assertions.assertThrows(RuntimeException.class, () -> userService.findById(1));
    }

}