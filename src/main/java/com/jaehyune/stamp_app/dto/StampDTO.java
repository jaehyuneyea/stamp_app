package com.jaehyune.stamp_app.dto;

import java.util.List;

/**
 * This class represents a DTO used for creating and retrieving a Stamp entity from the client or server side.
 * It is needed by StampRestController and StampService to create, read, update or delete a Stamp entity depending
 * on the HTTP request.
 * <p>
 * When calling a POST HTTP request, the JSON body should be formatted as such:
 * <p>
 * {
 *     "description": "Tokyo Stamp",
 *     "rating" : 4.5,
 *     "railway": "JR Line",
 *     "comments": []
 * }
 */
public class StampDTO {

    private Integer id;

    private String description;

    private float rating;

    private String railway;

    private List<CommentReadDTO> comments;

    private PhotoDTO photo;

    public StampDTO() {

    }

    /**
     *
     * @param id id of the stamp. This is not required when creating a new Stamp.
     * @param description the description of the stamp like name, location, etc.
     * @param rating rating of the stamp intended to be set by the users.
     * @param railway which railway it belongs to
     * @param photo the photo associated to the Stamp. This is not required when creaeting a new Stamp.
     */
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
