package com.jaehyune.stamp_app.service.impl;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Photo;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.entity.User;
import com.jaehyune.stamp_app.repository.CommentRepository;
import com.jaehyune.stamp_app.repository.StampRepository;
import com.jaehyune.stamp_app.repository.UserRepository;
import com.jaehyune.stamp_app.service.CommentService;
import com.jaehyune.stamp_app.service.ConverterMediator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private StampRepository stampRepository;
    private UserRepository userRepository;
    private ConverterMediator<Photo, PhotoDTO> photoConverter;

    public CommentServiceImpl(CommentRepository commentRepository, StampRepository stampRepository, UserRepository userRepository, ConverterMediator<Photo, PhotoDTO> photoConverter) {
        this.commentRepository = commentRepository;
        this.stampRepository = stampRepository;
        this.userRepository = userRepository;
        this.photoConverter = photoConverter;
    }

    @Transactional
    @Override
    public Comment save(CommentCreationDTO dto, Integer stamp_id) {
        if (stamp_id == null || stamp_id < 0) {
            throw new RuntimeException("Invalid ID: " + stamp_id);
        }
        Comment comment = toEntity(dto);
        // if the Comment is being updated, prevent the date_created from updating
        if (dto.getId() != null) {
            CommentReadDTO commentReadDTO = findById(dto.getId());
            comment.setDateCreated(commentReadDTO.getDateCreated());
        }
        Optional<Stamp> temp = stampRepository.findById(stamp_id);

        if (temp.isPresent()) {
            comment.setStampId(temp.get());
        } else {
            throw new RuntimeException("Did not find Stamp with ID: " + stamp_id);
        }
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public CommentReadDTO findById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isPresent()) {
            return toDto(comment.get());
        } else {
            throw new RuntimeException("Did not find Comment with ID: " + id);
        }
    }

    @Transactional
    @Override
    public List<CommentReadDTO> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::toDto).toList();
    }

    @Transactional
    @Override
    public List<CommentReadDTO> findAllCommentForStamp(Integer stamp_id) {
        Optional<Stamp> temp = stampRepository.findById(stamp_id);
        Stamp stamp;
        if (temp.isPresent()) {
            stamp = temp.get();
        } else {
            throw new RuntimeException("Did not find Comment with ID: " + stamp_id);
        }
        List<Comment> comments = stamp.getComments();
        return comments.stream().map(this::toDto).toList();
    }

    @Override
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }

    public Comment toEntity(CommentCreationDTO dto) {
        Integer userId = dto.getUserId();
        Optional<User> tempUser = userRepository.findById(userId);

        if (tempUser.isEmpty()) {
            throw new RuntimeException("Did not find User with ID: " + userId);
        }
        return Comment.builder()
                .id(dto.getId() != null ? dto.getId() : null)
                .description(dto.getDescription())
                .parentId(dto.getParentId())
                .userId(tempUser.get())
                .build();
//        if (dto.getId() != null) {
//            comment.setId(dto.getId());
//        }
//        String description = dto.getDescription();
//        Integer parentId = dto.getParentId();
//
//
//        comment.setDescription(description);
//        comment.setParentId(parentId);
    }

    @Override
    public Comment toEntity(CommentReadDTO dto) {
        return null;
    }

    @Override
    public CommentReadDTO toDto(Comment comment) {
        return CommentReadDTO.builder()
                .id(comment.getId())
                .description(comment.getDescription())
                .parentId(comment.getParentId())
                .username(comment.getUserId() != null ? comment.getUserId().getUsername() : null)
                .dateCreated(comment.getDateCreated())
                .photoDTOs(comment.getPhotos() != null ?
                        comment.getPhotos()
                        .stream()
                        .map(photo -> photoConverter.toDto(photo)).toList()
                        : Collections.emptyList())
                .build();
    }
}
