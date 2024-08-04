package com.jaehyune.stamp_app.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="comments")
public class Comment {
    // TODO: Ensure that the stamp endpoint also loads all comments associated with that stamp
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @Column(name = "stamp_id")
    private Integer stamp_id;

    @Column(name = "parent_id")
    private Integer parent_id;

    @Column(name = "description")
    private String description;

    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_created;

    public Comment() {

    }

    public Comment(Integer stamp_id, Integer parent_id, String description, Date date_created) {
        this.stamp_id = stamp_id;
        this.parent_id = parent_id;
        this.description = description;
        this.date_created = date_created;
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

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
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
}
