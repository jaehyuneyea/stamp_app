package com.jaehyune.stamp_app.dto;

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
public class UserCreationDTO {
    // used for only when the client sends info to create a user

    private String username;

    private String email;

    private String password;

    public UserCreationDTO() {

    }

    /**
     *
     * @param username name of the user that is to be set.
     * @param email email of the user
     * @param password password of the user that is to be set and hashed in the database.
     */
    public UserCreationDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
