package com.jaehyune.stamp_app.dto;

import java.util.Date;

public class CommentCreationDTO {
    // for creation it should be the user id instead
    private Integer id;
    private String description;
    private Date date_created;
    private Integer parent_id;
    private Integer user_id;

    public CommentCreationDTO() {

    }

    public CommentCreationDTO(String description, Date date_created, Integer parent_id, Integer user_id) {
        this.description = description;
        this.date_created = date_created;
        this.parent_id = parent_id;
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
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
}
