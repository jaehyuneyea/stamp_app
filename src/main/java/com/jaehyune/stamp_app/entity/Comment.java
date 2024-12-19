package com.jaehyune.stamp_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * This class represents the Comment entity. JPA persists the entity into the database.
 * <p>
 * Comment has a many-to-one relationship with Stamp, owning the many side.
 * Comment has a many-to-one relationship with User, owning the many side.
 * Comment has a one-to-many relationship with Photo, owning the one side.
 * Meaning Stamps can have many comments and Users can own many comments, and Comment can own many Photos.
 *<p>
 * We avoid using Lombok's @Data annotation, so it doesn't cause issues with lazy-loading attributes.
 *<p>
 * stampId: Object of the stamp. Required as an object for Entity relationships.
 * user: Object of the User. Required as an object for entity relationships.
 * parentId: id of the parent if it exists. Null if else.
 * description: contents of the comment.
 * dateCreated: date the comment was created. Uses pre-persist.
 * photos: photos associated with the comments. Null if none exist.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @PrePersist
    public void dateCreated() {
        this.dateCreated = new Date();
    }
}
