package com.jaehyune.stamp_app.repository;

import com.jaehyune.stamp_app.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The Repository layer for Photo.
 * We don't extend JPA Repository here because instead of saving the file to the database,
 * we save the file to the disk separately with an association to the filepath in the database which requires
 * a finer control than what JpaRepository provides out of the box.
 */
public interface PhotoRepository {
    Photo findById(String id);

    List<Photo> findAll();

    Photo save(Photo photo, MultipartFile image);

    void delete(String id);
}
