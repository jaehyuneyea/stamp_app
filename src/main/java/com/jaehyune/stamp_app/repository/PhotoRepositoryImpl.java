package com.jaehyune.stamp_app.repository;

import com.jaehyune.stamp_app.entity.Photo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhotoRepositoryImpl implements PhotoRepository {

    private EntityManager entityManager;

    public PhotoRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Photo findById(Integer id) {
        return entityManager.find(Photo.class, id);
    }

    @Override
    public List<Photo> findAll() {
        TypedQuery<Photo> photoTypedQuery = entityManager.createQuery("from Photo", Photo.class);
        List<Photo> photos = photoTypedQuery.getResultList();
        return photos;
    }

    @Override
    public Photo save(Photo photo) {
        return entityManager.merge(photo);
        // TODO: We should perform disk operations here.
    }

    @Override
    public void delete(Integer id) {
        Photo photo = entityManager.find(Photo.class, id);
        entityManager.remove(photo);
        // TODO: We should perform disk operations here.
    }
}
