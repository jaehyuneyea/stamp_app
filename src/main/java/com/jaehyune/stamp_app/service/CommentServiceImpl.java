package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new RuntimeException("Did not find Comment with ID: " + id);
        }
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }
}
