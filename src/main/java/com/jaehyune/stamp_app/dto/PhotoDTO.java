package com.jaehyune.stamp_app.dto;

/**
 * This class represents a DTO used for retrieving a Photo entity from the server side.
 * This refers to only the metadata, not the actual file.
 * It is used to retrieve data about the photos associated to either Comment or Stamp entities and is needed by
 * StampDTO, CommentReadDTO, CommentService, StampService and PhotoService.
 */
public class PhotoDTO {
    private String id;

    private Integer stamp_id;

    private Integer comment_id;

    private String filePath;


    public PhotoDTO() {

    }

    /**
     *
     * @param stamp_id the id of the Stamp the photo is associated to if it exists. Null if else.
     * @param comment_id the id of the Comment the photo is associated to if it exists. Null if else.
     * @param filePath the path of where the file exists. Stored as a string.
     */
    public PhotoDTO(Integer stamp_id, Integer comment_id, String filePath) {
        this.stamp_id = stamp_id;
        this.comment_id = comment_id;
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStamp_id() {
        return stamp_id;
    }

    public void setStamp_id(Integer stamp_id) {
        this.stamp_id = stamp_id;
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
