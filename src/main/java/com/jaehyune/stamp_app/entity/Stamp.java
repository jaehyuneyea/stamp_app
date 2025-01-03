package com.jaehyune.stamp_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
/**
 * This class represents the Stamp entity. JPA persists the entity into the database.
 * <p>
 * Stamp has a one-to-many relationship with Comment, owning the one side.
 * Stamp has a one-to-one relationship with Photo, owning the one side.
 * Meaning Stamps can have one Photo and Stamp can own many Comments.
 * description: description of the stamp
 * rating: rating of the stamp in float
 * railway: the collection of railway that the stamp is in
 * photo: the photo of the correlated stamp
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
