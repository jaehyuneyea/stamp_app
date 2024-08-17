package com.jaehyune.stamp_app.dto;

import java.util.List;

public class StampDTO {
    // TODO: You left off here, return photos in stampDTO then test and handle disk storage
    // TODO: and also test passing in photo without id; I think we need to pass in multipartFile from the controller.
    // TODO: Handle multipart form data and the photoDTO separately

    private Integer id;

    private String description;

    private float rating;

    private String railway;

    private List<CommentReadDTO> comments;

    private PhotoDTO photo;

    public StampDTO() {

    }

    public StampDTO(Integer id, String description, float rating, String railway, PhotoDTO photo) {
        this.id = id;
        this.description = description;
        this.rating = rating;
        this.railway = railway;
        this.photo = photo;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getRailway() {
        return railway;
    }

    public void setRailway(String railway) {
        this.railway = railway;
    }

    public List<CommentReadDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentReadDTO> comments) {
        this.comments = comments;
    }

    public PhotoDTO getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDTO photo) {
        this.photo = photo;
    }
}
