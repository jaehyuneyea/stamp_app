package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.CommentDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private StampService stampService;

    public CommentServiceImpl(CommentRepository commentRepository, StampService stampService) {
        this.commentRepository = commentRepository;
        this.stampService = stampService;
    }

    public CommentDTO convertToDTO(Comment comment) {
        String description = comment.getDescription();
        Date date = comment.getDate_created();
        Integer parent_id = comment.getParent_id();
        Integer id = comment.getId();
        return new CommentDTO(id, description , date, parent_id);
    }

    public Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        String description = commentDTO.getDescription();
        Date date = commentDTO.getDate_created();
        Integer parent_id = commentDTO.getParent_id();
        Integer id = commentDTO.getId();

        comment.setId(id);
        comment.setDescription(description);
        comment.setDate_created(date);
        comment.setParent_id(parent_id);

        return comment;
    }

    @Override
    public Comment save(CommentDTO dto, Integer stamp_id) {
        if (stamp_id == null || stamp_id < 0) {
            throw new RuntimeException("Invalid ID: " + stamp_id);
        }
        Comment comment = convertToEntity(dto);
        Stamp stamp = stampService.findById(stamp_id);
        comment.setStamp_id(stamp);
        return commentRepository.save(comment);
    }

    @Override
    public CommentDTO findById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isPresent()) {
            return convertToDTO(comment.get());
        } else {
            throw new RuntimeException("Did not find Comment with ID: " + id);
        }
    }

    @Override
    public List<CommentDTO> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<CommentDTO> findAllCommentForStamp(Integer stamp_id) {
        List<Comment> comments = stampService.findById(stamp_id).getComments();
        return comments.stream().map(this::convertToDTO).toList();
    }

    @Override
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }
}
