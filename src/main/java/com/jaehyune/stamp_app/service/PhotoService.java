package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.entity.Photo;

import java.util.List;

public interface PhotoService extends ConverterMediator<Photo, PhotoDTO> {
    Photo save(PhotoDTO dto);

    PhotoDTO findById(Integer id);

    List<PhotoDTO> findAll();

    void delete(Integer id);
}
