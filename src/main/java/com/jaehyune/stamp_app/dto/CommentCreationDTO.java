package com.jaehyune.stamp_app.dto;

import java.util.Date;
import java.util.List;

/**
 * This class represents a DTO used for creating a Comment entity from the client side.
 * It is needed by CommentRestController and CommentService to create a new Comment entity using the values passed
 * in from the client.
 * <p>
 * When calling a POST HTTP request, the JSON body should be formatted as such:
 * <p>
 * {
 *     "description": "pretty good stamp",
 *     "parent_id": null,
 *     "user_id": 1
 * }
 */
public class CommentCreationDTO {
    private String description;
    private Integer parent_id;
    private Integer user_id;

    public CommentCreationDTO() {

    }

    /**
     *
     * @param description the description for the comment that is being written.
     * @param parent_id the id of the parent comment if it exists. If not, it is null.
     * @param user_id the id of the user that is posting the comment.
     */
    public CommentCreationDTO(String description, Integer parent_id, Integer user_id) {
        this.description = description;
        this.parent_id = parent_id;
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

}
