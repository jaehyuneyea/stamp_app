package com.jaehyune.stamp_app.dto;

/**
 * This class represents a DTO used for retrieving a User entity from the server side.
 * It is needed by UserRestController and UserService to retrieve a User entity from the server.
 */
public class UserDTO {

    private Integer id;

    private String username;

    private String email;

    public UserDTO() {

    }

    /**
     *
     * @param id id of the user
     * @param username name of the user
     * @param email email of the user
     */
    public UserDTO(Integer id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
