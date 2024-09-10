package com.jaehyune.stamp_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
/**
 * This class represents the Stamp entity. JPA persists the entity into the database.
 * <p>
 * Stamp has a one-to-many relationship with Comment, owning the one side.
 * Stamp has a one-to-one relationship with Photo, owning the one side.
 * Meaning Stamps can have one Photo and Stamp can own many Comments.
 */
@Entity
@Table(name="stamps")
public class Stamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="description")
    private String description;

    @Column(name="rating")
    private float rating;

    @Column(name="railway")
    private String railway;

    @OneToMany(mappedBy = "stampId")
    @JsonManagedReference
    private List<Comment> comments;

    @OneToOne(mappedBy = "stamp")
    @JsonManagedReference
    private Photo photo;

    public Stamp() {

    }

    /**
     *
     * @param description description of the stamp
     * @param rating rating of the stamp in float
     * @param railway the collection of railway that the stamp is in
     * @param photo the photo of the correlated stamp
     */
    public Stamp(String description, float rating, String railway, Photo photo) {
        this.description = description;
        this.rating = rating;
        this.railway = railway;
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getRailway() {
        return railway;
    }

    public void setRailway(String railway) {
        this.railway = railway;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
