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
    private Date date_created;
    private Integer parent_id;
    private String username;
    private List<PhotoDTO> photos;

    /**
     *
     * @param id the id of the comment
     * @param description the contents of the comment
     * @param date_created the date the comment was created. This is a Date object.
     * @param parent_id the id of the parent comment if it exists. If not, it is null.
     * @param username the name of the user that posted the comment.
     * @param photos the metadata of the photos attached to the comment if it exists. If not, it is null.
     */
    public CommentReadDTO(Integer id, String description, Date date_created, Integer parent_id, String username, List<PhotoDTO> photos) {
        this.id = id;
        this.description = description;
        this.username = username;
        this.date_created = date_created;
        this.parent_id = parent_id;
        this.photos = photos;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
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

    public List<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDTO> photos) {
        this.photos = photos;
    }
}
