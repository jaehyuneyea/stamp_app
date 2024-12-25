package com.jaehyune.stamp_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
/**
 * This class represents the Photo entity which is the metadata of the file. JPA persists the entity into the database.
 * <p>
 * Photo has a one-to-one relationship with Stamp, owning the one side.
 * Photo has a many-to-one relationship with Comment, owning the many side.
 * Meaning Stamps can have one Photo and Comments can own many Photos.
 * <p>
 * Photo's ID is stored as a UUID.
 *
 *  stamp Object of the stamp. Required as an object for Entity relationships.
 *  comment Object of the comment. Required as an object for Entity relationships.
 *  filePath path of the file stored as String. This is set during persistence.
 *  dateCreated date the photo was created. Uses pre-persist.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "stamp_id") // owning side (one with foreign key) gets the @JoinColumn
    @JsonBackReference
    private Stamp stamp;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    @JsonBackReference
    private Comment comment;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "date_created")
    private Date dateCreated;

    @PrePersist // fetch the date programmatically before persisting
    public void dateCreated() {
        this.dateCreated = new Date();
    }
}
