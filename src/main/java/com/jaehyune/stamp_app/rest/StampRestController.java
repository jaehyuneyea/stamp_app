package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.rest.error.IdNotFoundException;
import com.jaehyune.stamp_app.service.PhotoService;
import com.jaehyune.stamp_app.service.StampService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * The Rest Controller for Stamps. This handles creating, updating, deleting and reading stamp entities.
 * It delegates the work to StampService, and any photo persistence to PhotoService.
 *
 * This is generally reserved for testing / admin use as well, since all stamp data will be obtained from
 * an external source.
 */
@RestController
public class StampRestController {

    private StampService stampService;
    private PhotoService photoService;

    public StampRestController(StampService stampService, PhotoService photoService) {
        this.stampService = stampService;
        this.photoService = photoService;
    }

    // add stamp
    @PostMapping("/stamps")
    public Stamp addStamp(@RequestPart StampDTO dto,
                          @RequestPart Optional<MultipartFile> image) {
        if (dto.getId() != null) {
            throw new RuntimeException("ID field should not exist for creating stamps");
        }
        Stamp stamp = stampService.save(dto);
        if (image.isPresent()) {
            PhotoDTO photoDTO = PhotoDTO.builder().build();
            photoDTO.setStampId(stamp.getId());
            stamp.setPhoto(photoService.save(photoDTO, image.get()));
        }
        return stamp;
    }
    // TODO: Handle updates with images
    @PutMapping("/stamps")
    public Stamp updateStamp(@RequestBody StampDTO dto) {
        if (dto.getId() == null) {
            throw new RuntimeException("ID field should not be null for editting stamps");
        }
        return stampService.save(dto);
    }

    // read stamp by id
    @GetMapping("/stamps/{id}")
    public StampDTO findById(@PathVariable Integer id) {
        if (id < 0) {
            throw new IdNotFoundException("Invalid ID: " + id);
        }
        return stampService.findById(id);
    }

    // read all stamps
    @GetMapping("/stamps")
    public List<StampDTO> findAll() {
        return stampService.findAll();
    }

    // delete stamp by id
    @DeleteMapping("/stamps/{id}")
    public void deleteStamp(@PathVariable Integer id) {
        if (id < 0) {
            throw new IdNotFoundException("Invalid ID: " + id);
        }
        StampDTO stampDTO = stampService.findById(id);
        PhotoDTO photoDTO = stampDTO.getPhoto();
        if (photoDTO != null) {
            photoService.delete(photoDTO.getId());
        }
        stampService.delete(id);
    }
}
