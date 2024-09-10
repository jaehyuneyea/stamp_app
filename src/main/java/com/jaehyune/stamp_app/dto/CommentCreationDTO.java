package com.jaehyune.stamp_app.dto;

/**
 * This class represents a DTO used for creating a Comment entity from the client side.
 * It is needed by CommentRestController and CommentService to create a new Comment entity using the values passed
 * in from the client.
 * <p>
 * When calling a POST HTTP request, the JSON body should be formatted as such:
 * <p>
 * {
 *     "id": null,
 *     "description": "pretty good stamp",
 *     "parent_id": null,
 *     "user_id": 1
 * }
 */
public class CommentCreationDTO {
    private Integer id;
    private String description;
    private Integer parentId;
    private Integer userId;

    public CommentCreationDTO() {

    }

    /**
     *
     * @param description the description for the comment that is being written.
     * @param parentId the id of the parent comment if it exists. If not, it is null.
     * @param userId the id of the user that is posting the comment.
     */
    public CommentCreationDTO(String description, Integer parentId, Integer userId) {
        this.description = description;
        this.parentId = parentId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
