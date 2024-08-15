package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.entity.Photo;
import com.jaehyune.stamp_app.service.PhotoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PhotoRestController {

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
    public Photo save(@RequestBody PhotoDTO photo) {
        Photo tempPhoto = photoService.save(photo);
        return tempPhoto;
    }

    @DeleteMapping("/photos/{photo_id}")
    public void delete(@PathVariable Integer photo_id) {
        photoService.delete(photo_id);
    }
}
