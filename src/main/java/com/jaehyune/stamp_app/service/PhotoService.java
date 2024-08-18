package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService extends ConverterMediator<Photo, PhotoDTO> {
    Photo save(PhotoDTO dto, MultipartFile image);

    PhotoDTO findById(String id);

    List<PhotoDTO> findAll();

    void delete(String id);
}
