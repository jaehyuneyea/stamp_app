package com.jaehyune.stamp_app.dto;

import lombok.Builder;
import lombok.Data;

/**
 * This class represents a DTO used for creating a User entity from the client side.
 * It is needed by UserRestController and UserService to create a new User entity using the values passed
 * in from the client.
 * <p>
 * It will later be used for authentication and authorization as well.
 * <p>
 * When calling a POST HTTP request, the JSON body should be formatted as such:
 * <p>
 * {
 *     "username": "some_username",
 *     "email": "username@email.com",
 *     "password": "password123"
 * }
 */
@Data
@Builder
public class UserCreationDTO {
    // used for only when the client sends info to create a user
    /**
     * username: name of the user that is to be set.
     * email: email of the user
     * password: password of the user that is to be set and hashed in the database.
     */
    private String username;
    private String email;
    private String password;
}
