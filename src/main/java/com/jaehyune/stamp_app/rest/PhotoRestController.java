package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.entity.Photo;
import com.jaehyune.stamp_app.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Reserved for testing. Likely the client should never need to upload a photo on its own without any association
 * to comment or stamp.
 */
@RestController
public class PhotoRestController {
    // TODO: You left off here, pass in Multifile form data and handle disk saving.
    private PhotoService photoService;

    public PhotoRestController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/photos")
    public List<PhotoDTO> findAll() {
        return photoService.findAll();
    }

    @GetMapping("/photos/{photo_id}")
    public PhotoDTO find(@PathVariable Integer photo_id) {
        return photoService.findById(photo_id);
    }

    @PostMapping("/photos")
    public Photo save(@RequestPart PhotoDTO photoMetadata,
                      @RequestPart MultipartFile image) {
        Photo tempPhoto = photoService.save(photoMetadata, image);
        return tempPhoto;
    }

    @DeleteMapping("/photos/{photo_id}")
    public void delete(@PathVariable Integer photo_id) {
        photoService.delete(photo_id);
    }
}
