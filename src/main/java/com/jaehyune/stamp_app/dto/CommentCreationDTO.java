package com.jaehyune.stamp_app.dto;

import lombok.*;

/**
 * This class represents a DTO used for creating a Comment entity from the client side.
 * It is needed by CommentRestController and CommentService to create a new Comment entity using the values passed
 * in from the client.
 * <p>
 *      description: the description for the comment that is being written.
 *      parentId: the id of the parent comment if it exists. If not, it is null.
 *      userId: the id of the user that is posting the comment.
 * When calling a POST HTTP request, the JSON body should be formatted as such:
 * <p>
 * {
 *     "id": null,
 *     "description": "pretty good stamp",
 *     "parent_id": null,
 *     "user_id": 1
 * }
 */
@Builder(toBuilder = true)
@Data
public class CommentCreationDTO {
    private Integer id;
    private String description;
    private Integer parentId;
    private Integer userId;
}
