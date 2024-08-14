package com.jaehyune.stamp_app.dto;

public class PhotoDTO {

    private Integer id;

    private Integer stamp_id;

    private Integer comment_id;


    public PhotoDTO() {

    }

    public PhotoDTO(Integer stamp_id, Integer comment_id) {
        this.stamp_id = stamp_id;
        this.comment_id = comment_id;
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
}
