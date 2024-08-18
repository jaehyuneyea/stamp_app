package com.jaehyune.stamp_app.dto;

import java.util.Date;
import java.util.List;

public class CommentCreationDTO {
    // for creation it should be the user id instead
    private String description;
    private Integer parent_id;
    private Integer user_id;
    private List<PhotoDTO> photos;

    public CommentCreationDTO() {

    }

    public CommentCreationDTO(String description, Integer parent_id, Integer user_id, List<PhotoDTO> photos) {
        this.description = description;
        this.parent_id = parent_id;
        this.user_id = user_id;
        this.photos = photos;
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

    public List<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDTO> photos) {
        this.photos = photos;
    }
}
