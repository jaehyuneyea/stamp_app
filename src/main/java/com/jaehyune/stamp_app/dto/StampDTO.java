package com.jaehyune.stamp_app.dto;

import lombok.Builder;
import lombok.Data;

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
@Builder
@Data
public class StampDTO {

    /**
     * id: id of the stamp. This is not required when creating a new Stamp.
     * description: the description of the stamp like name, location, etc.
     * rating: rating of the stamp intended to be set by the users.
     * railway: which railway it belongs to
     * photo: the photo associated to the Stamp. This is not required when creaeting a new Stamp.
     */
    private Integer id;
    private String description;
    private float rating;
    private String railway;
    private List<CommentReadDTO> comments;
    private PhotoDTO photo;
}
