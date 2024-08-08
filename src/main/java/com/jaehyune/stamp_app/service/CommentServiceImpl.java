package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.CommentDTO;
import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.repository.CommentRepository;
import com.jaehyune.stamp_app.repository.StampRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private StampRepository stampRepository;

    public CommentServiceImpl(CommentRepository commentRepository, StampRepository stampRepository) {
        this.commentRepository = commentRepository;
        this.stampRepository = stampRepository;
    }

    @Override
    public Comment save(CommentDTO dto, Integer stamp_id) {
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
    public CommentDTO findById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isPresent()) {
            return toDto(comment.get());
        } else {
            throw new RuntimeException("Did not find Comment with ID: " + id);
        }
    }

    @Override
    public List<CommentDTO> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::toDto).toList();
    }

    @Override
    public List<CommentDTO> findAllCommentForStamp(Integer stamp_id) {
//        List<Comment> comments = stampService.findById(stamp_id).getComments();
//        return comments.stream().map(this::convertToDTO).toList();
        // TODO: Move stamp service reference to another layer
        return List.of();
    }

    @Override
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }

    public Comment toEntity(CommentDTO dto) {
        Comment comment = new Comment();
        String description = dto.getDescription();
        Date date = dto.getDate_created();
        Integer parent_id = dto.getParent_id();
        Integer id = dto.getId();

        comment.setId(id);
        comment.setDescription(description);
        comment.setDate_created(date);
        comment.setParent_id(parent_id);

        return comment;
    }
    @Override
    public CommentDTO toDto(Comment comment) {
        String description = comment.getDescription();
        Date date = comment.getDate_created();
        Integer parent_id = comment.getParent_id();
        Integer id = comment.getId();
        return new CommentDTO(id, description , date, parent_id);
    }
}
