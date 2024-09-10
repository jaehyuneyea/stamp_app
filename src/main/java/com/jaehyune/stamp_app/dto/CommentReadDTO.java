package com.jaehyune.stamp_app.dto;

import java.util.Date;
import java.util.List;

/**
 * This class represents a DTO used for retrieving a Comment entity from the server side.
 * It is needed by CommentRestController and CommentService when using GET requests to retrieve specific Comments.
 */
public class CommentReadDTO {
    // contains the field you want to expose with the getters and setters and constructors

    private Integer id;
    private String description;
    private Date dateCreated;
    private Integer parentId;
    private String username;
    private List<PhotoDTO> photoDTOs;

    /**
     *
     * @param id the id of the comment
     * @param description the contents of the comment
     * @param dateCreated the date the comment was created. This is a Date object.
     * @param parentId the id of the parent comment if it exists. If not, it is null.
     * @param username the name of the user that posted the comment.
     * @param photoDTOs the metadata of the photos attached to the comment if it exists. If not, it is null.
     */
    public CommentReadDTO(Integer id, String description, Date dateCreated, Integer parentId, String username, List<PhotoDTO> photoDTOs) {
        this.id = id;
        this.description = description;
        this.username = username;
        this.dateCreated = dateCreated;
        this.parentId = parentId;
        this.photoDTOs = photoDTOs;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public List<PhotoDTO> getPhotoDTOs() {
        return photoDTOs;
    }

    public void setPhotoDTOs(List<PhotoDTO> photoDTOs) {
        this.photoDTOs = photoDTOs;
    }
}
