package com.jaehyune.stamp_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // TODO: possibly change to GUID later
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "stamp_id") // owning side (one with foreign key) gets the @JoinColumn
    // TODO: Need backreference?
    private Stamp stamp;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    @JsonBackReference
    private Comment comment;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "date_created")
    private Date dateCreated;

    public Photo() {

    }

    public Photo(Stamp stamp, Comment comment, String filePath, Date dateCreated) {
        this.stamp = stamp;
        this.comment = comment;
        this.filePath = filePath;
        this.dateCreated = dateCreated;
    }

    @PrePersist // fetch the date programmatically before persisting
    public void dateCreated() {
        this.dateCreated = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    private void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
