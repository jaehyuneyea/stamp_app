package com.jaehyune.stamp_app.dto;

import lombok.Builder;
import lombok.Data;

/**
 * This class represents a DTO used for retrieving a Photo entity from the server side.
 * This refers to only the metadata, not the actual file.
 * It is used to retrieve data about the photos associated to either Comment or Stamp entities and is needed by
 * StampDTO, CommentReadDTO, CommentService, StampService and PhotoService.
 */
@Builder
@Data
public class PhotoDTO {
    /**
     * stampId: the id of the Stamp the photo is associated to if it exists. Null if else.
     * commentId: the id of the Comment the photo is associated to if it exists. Null if else.
     * filePath: the path of where the file exists. Stored as a string.
     */
    private String id;
    private Integer stampId;
    private Integer commentId;
    private String filePath;
}
