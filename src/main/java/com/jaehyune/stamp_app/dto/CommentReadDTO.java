package com.jaehyune.stamp_app.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * This class represents a DTO used for retrieving a Comment entity from the server side.
 * It is needed by CommentRestController and CommentService when using GET requests to retrieve specific Comments.
 */
@Builder
@Data
public class CommentReadDTO {
    // contains the field you want to expose with the getters and setters and constructors
    /**
     * id: the id of the comment
     * description: the contents of the comment
     * dateCreated: the date the comment was created. This is a Date object.
     * parentId: the id of the parent comment if it exists. If not, it is null.
     * username: the name of the user that posted the comment.
     * photoDTOs: the metadata of the photos attached to the comment if it exists. If not, it is null.
     */
    private Integer id;
    private String description;
    private Date dateCreated;
    private Integer parentId;
    private String username;
    private List<PhotoDTO> photoDTOs;
}
