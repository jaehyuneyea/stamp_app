package com.jaehyune.stamp_app.dto;

import java.util.List;

public class StampDTO {

    private Integer id;

    private String description;

    private float rating;

    private String railway;

    private List<CommentDTO> comments;

    public StampDTO() {

    }

    public StampDTO(Integer id, String description, float rating, String railway) {
        this.id = id;
        this.description = description;
        this.rating = rating;
        this.railway = railway;
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

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
