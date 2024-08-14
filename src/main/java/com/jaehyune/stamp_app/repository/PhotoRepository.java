package com.jaehyune.stamp_app.repository;

import com.jaehyune.stamp_app.entity.Photo;

import java.util.List;

/**
 * We use EntityManager instead of JPA Repository here for a finer control of saving to disk.
 */
public interface PhotoRepository {
    Photo findById(int id);

    List<Photo> findAll();

    Photo save(Photo photo);

    void delete(int id);
}
