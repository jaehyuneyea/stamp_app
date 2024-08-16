package com.jaehyune.stamp_app.dto;

/**
 * This is the metadata for the image that will be passed in along with the actual file.
 * filePath is not required when being passed in, but required when being from the database.
 */
public class PhotoDTO {

    private Integer id;

    private Integer stamp_id;

    private Integer comment_id;

    private String filePath;


    public PhotoDTO() {

    }

    public PhotoDTO(Integer stamp_id, Integer comment_id, String filePath) {
        this.stamp_id = stamp_id;
        this.comment_id = comment_id;
        this.filePath = filePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
