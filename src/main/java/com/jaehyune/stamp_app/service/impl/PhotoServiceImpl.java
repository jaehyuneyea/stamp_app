package com.jaehyune.stamp_app.service.impl;

import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Photo;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.repository.CommentRepository;
import com.jaehyune.stamp_app.repository.PhotoRepository;
import com.jaehyune.stamp_app.repository.StampRepository;
import com.jaehyune.stamp_app.service.PhotoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoServiceImpl implements PhotoService {

    private PhotoRepository photoRepository;
    private CommentRepository commentRepository;
    private StampRepository stampRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository, CommentRepository commentRepository, StampRepository stampRepository) {
        this.photoRepository = photoRepository;
        this.commentRepository = commentRepository;
        this.stampRepository = stampRepository;
    }

    @Override
    @Transactional
    public Photo save(PhotoDTO dto, MultipartFile image) {
        return photoRepository.save(toEntity(dto), image);
    }

    @Override
    public PhotoDTO findById(String id) {
        return toDto(photoRepository.findById(id));
    }

    @Override
    public List<PhotoDTO> findAll() {
        List<Photo> photos = photoRepository.findAll();
        return photos.stream().map(this::toDto).toList();
    }

    @Override
    @Transactional
    public void delete(String id) {
        photoRepository.delete(id);
    }

    @Override
    public Photo toEntity(PhotoDTO dto) {
        Photo photo = new Photo();
        if (dto.getComment_id() != null) {
            Optional<Comment> optComment = commentRepository.findById(dto.getComment_id());
            if (optComment.isPresent()) {
                photo.setComment(optComment.get());
            } else {
                throw new RuntimeException("Did not find Comment with ID: " + dto.getComment_id());
            }
        }
        if (dto.getStamp_id() != null) {
            Optional<Stamp> optStamp = stampRepository.findById(dto.getStamp_id());
            if (optStamp.isPresent()) {
                photo.setStamp(optStamp.get());
            } else {
                throw new RuntimeException("Did not find Stamp with ID: " + dto.getStamp_id());
            }

        }
        return photo;
    }

    @Override
    public PhotoDTO toDto(Photo photo) {
        PhotoDTO dto = new PhotoDTO();
        dto.setId(photo.getId());
        if (photo.getComment() != null) {
            dto.setComment_id(photo.getComment().getId());
        } else {
            dto.setComment_id(null);
        }
        if (photo.getStamp() != null) {
            dto.setStamp_id(photo.getStamp().getId());
        } else {
            dto.setStamp_id(null);
        }
        dto.setFilePath(photo.getFilePath());
        return dto;
    }
}
