package com.jaehyune.stamp_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * This class represents the Comment entity. JPA persists the entity into the database.
 * <p>
 * Comment has a many-to-one relationship with Stamp, owning the many side.
 * Comment has a many-to-one relationship with User, owning the many side.
 * Comment has a one-to-many relationship with Photo, owning the one side.
 * Meaning Stamps can have many comments and Users can own many comments, and Comment can own many Photos.
 *
 */
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
    private Stamp stampId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User userId;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "description")
    private String description;

    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @OneToMany(mappedBy = "comment")
    @JsonManagedReference
    private List<Photo> photos;

    public Comment() {

    }

    /**
     *
     * @param stampId Object of the stamp. Required as an object for Entity relationships.
     * @param user Object of the User. Required as an object for entity relationships.
     * @param parentId id of the parent if it exists. Null if else.
     * @param description contents of the comment.
     * @param dateCreated date the comment was created. Uses pre-persist.
     * @param photos photos associated with the comments. Null if none exist.
     */
    public Comment(Stamp stampId, User user, Integer parentId, String description, Date dateCreated, List<Photo> photos) {
        this.stampId = stampId;
        this.userId = user;
        this.parentId = parentId;
        this.description = description;
        this.dateCreated = dateCreated;
        this.photos = photos;
    }

    @PrePersist
    public void dateCreated() {
        this.dateCreated = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Stamp getStampId() {
        return stampId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public void setStampId(Stamp stampId) {
        this.stampId = stampId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
