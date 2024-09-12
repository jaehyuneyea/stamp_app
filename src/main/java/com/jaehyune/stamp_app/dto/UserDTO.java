package com.jaehyune.stamp_app.dto;

import lombok.Builder;
import lombok.Data;

/**
 * This class represents a DTO used for retrieving a User entity from the server side.
 * It is needed by UserRestController and UserService to retrieve a User entity from the server.
 */
@Builder
@Data
public class UserDTO {
    /**
     * id: id of the user
     * username: name of the user
     * email: email of the user
     */
    private Integer id;
    private String username;
    private String email;
}
