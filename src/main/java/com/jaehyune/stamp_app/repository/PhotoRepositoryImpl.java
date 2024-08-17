package com.jaehyune.stamp_app.repository;

import com.jaehyune.stamp_app.entity.Photo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
    public Photo save(Photo photo, MultipartFile image) {
        String filepath = "C:/Users/Alpha PC/Desktop/repos/stamp-app/images";
        photo.setId(UUID.randomUUID().toString());
        try {
            Path path = Paths.get(filepath);
            if (!Files.exists(path)) {
                Files.createDirectory(path); // create if not already exists
            }
            InputStream imageData = image.getInputStream();
            Path imagePath = path.resolve(photo.getId() + ".jpg");
            photo.setFilePath(imagePath.toString());
            Files.copy(imageData, imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entityManager.merge(photo);
    }

    @Override
    public void delete(String id) {
        Photo photo = entityManager.find(Photo.class, id);
        try {
            String filepath = "C:/Users/Alpha PC/Desktop/repos/stamp-app/images";
            Path imagePath = Paths.get(filepath).resolve(photo.getId() + ".jpg");
            Files.delete(imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        entityManager.remove(photo);
    }
}
