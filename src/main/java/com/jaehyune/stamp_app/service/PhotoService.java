package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service layer for Photo. Mediates between PhotoRestController and PhotoRepository.
 * Used also by CommentRestController and StampRestController.
 */
public interface PhotoService extends ConverterMediator<Photo, PhotoDTO> {
    /**
     * Save the photo metadata to a database, and the image file to disk and return the complete photo metadata
     * @param dto photo metadata passed in from the user
     * @param image the image file to be saved to disk
     * @return complete photo metadata with filepath and date_created.
     */
    Photo save(PhotoDTO dto, MultipartFile image);

    /**
     * Find a photo by id.
     * @param id id of the photo that needs to be found. It is in UUID
     * @return The DTO of photo
     */
    PhotoDTO findById(String id);

    /**
     * Find all photos.
     * @return a list of all Photo metadata. Return an empty list if else.
     */
    List<PhotoDTO> findAll();

    /**
     * Delete a photo by id.
     * @param id id of the photo that needs to be deleted. It is in UUID
     */
    void delete(String id);
}
