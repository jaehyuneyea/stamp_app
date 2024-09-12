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
    @Transactional
    public PhotoDTO findById(String id) {
        return toDto(photoRepository.findById(id));
    }

    @Override
    @Transactional
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
        if (dto.getCommentId() != null) {
            Optional<Comment> optComment = commentRepository.findById(dto.getCommentId());
            if (optComment.isPresent()) {
                photo.setComment(optComment.get());
            } else {
                throw new RuntimeException("Did not find Comment with ID: " + dto.getCommentId());
            }
        }
        if (dto.getStampId() != null) {
            Optional<Stamp> optStamp = stampRepository.findById(dto.getStampId());
            if (optStamp.isPresent()) {
                photo.setStamp(optStamp.get());
            } else {
                throw new RuntimeException("Did not find Stamp with ID: " + dto.getStampId());
            }

        }
        return photo;
    }

    @Override
    public PhotoDTO toDto(Photo photo) {
        return PhotoDTO.builder()
                .id(photo.getId())
                .commentId(photo.getComment() != null ? photo.getComment().getId() : null)
                .stampId(photo.getStamp() != null ? photo.getStamp().getId() : null)
                .filePath(photo.getFilePath())
                .build();
    }
}
