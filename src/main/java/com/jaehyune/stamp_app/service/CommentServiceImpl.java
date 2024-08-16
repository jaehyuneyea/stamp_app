package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.dto.UserDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Photo;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.entity.User;
import com.jaehyune.stamp_app.repository.CommentRepository;
import com.jaehyune.stamp_app.repository.StampRepository;
import com.jaehyune.stamp_app.repository.UserRepository;
import org.springframework.stereotype.Service;

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

    @Override
    public Comment save(CommentCreationDTO dto, Integer stamp_id) {
        if (stamp_id == null || stamp_id < 0) {
            throw new RuntimeException("Invalid ID: " + stamp_id);
        }
        Comment comment = toEntity(dto);
        Optional<Stamp> temp = stampRepository.findById(stamp_id);

        if (temp.isPresent()) {
            comment.setStamp_id(temp.get());
        } else {
            throw new RuntimeException("Did not find Stamp with ID: " + stamp_id);
        }
        return commentRepository.save(comment);
    }

    @Override
    public CommentReadDTO findById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isPresent()) {
            return toDto(comment.get());
        } else {
            throw new RuntimeException("Did not find Comment with ID: " + id);
        }
    }

    @Override
    public List<CommentReadDTO> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::toDto).toList();
    }

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
        Comment comment = new Comment();
        String description = dto.getDescription();
        Integer parent_id = dto.getParent_id();
        Integer user_id = dto.getUser_id();

        comment.setDescription(description);
        comment.setParent_id(parent_id);

        Optional<User> tempUser = userRepository.findById(user_id);

        if (tempUser.isPresent()) {
            comment.setUser_id(tempUser.get());
        } else {
            throw new RuntimeException("Did not find User with ID: " + user_id);
        }
        List<PhotoDTO> photoDTOS = dto.getPhotos();
        comment.setPhotos(photoDTOS.stream().map(photoDTO -> photoConverter.toEntity(photoDTO)).toList());
        return comment;
    }

    @Override
    public Comment toEntity(CommentReadDTO dto) {
        return null;
    }

    @Override
    public CommentReadDTO toDto(Comment comment) {
        String description = comment.getDescription();
        Date date = comment.getDate_created();
        Integer parent_id = comment.getParent_id();
        Integer id = comment.getId();
        String username = comment.getUser_id().getUsername();
        List<PhotoDTO> photoDTOS;
        try {
            List<Photo> photos = comment.getPhotos();
            photoDTOS = photos.stream().map(photo -> photoConverter.toDto(photo)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new CommentReadDTO(id, description , date, parent_id, username, photoDTOS);
    }
}
