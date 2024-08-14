package com.jaehyune.stamp_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "stamp_id")
    @JsonBackReference
    private Stamp stamp_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user_id;

    @Column(name = "parent_id")
    private Integer parent_id;

    @Column(name = "description")
    private String description;

    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_created;

    @OneToMany(mappedBy = "comment")
    @JsonManagedReference
    private List<Photo> photos;

    public Comment() {

    }

    public Comment(Stamp stamp_id, User user, Integer parent_id, String description, Date date_created, List<Photo> photos) {
        this.stamp_id = stamp_id;
        this.user_id = user;
        this.parent_id = parent_id;
        this.description = description;
        this.date_created = date_created;
        this.photos = photos;
    }

    @PrePersist
    public void date_created() {
        this.date_created = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Stamp getStamp_id() {
        return stamp_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public void setStamp_id(Stamp stamp_id) {
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
