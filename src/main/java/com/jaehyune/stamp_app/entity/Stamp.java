package com.jaehyune.stamp_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="stamps")
public class Stamp {

    @Column(name="id")
    private Integer id;

    @Column(name="description")
    private String description;

    @Column(name="rating")
    private float rating;

    @Column(name="railway")
    private String railway;

    public Stamp() {

    }

    public Stamp(String description, float rating, String railway) {
        this.description = description;
        this.rating = rating;
        this.railway = railway;
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
}
