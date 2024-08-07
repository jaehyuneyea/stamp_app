package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.CommentDTO;
import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Stamp;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConverterMediatorImpl implements ConverterMediator {

    private CommentService commentService;
    private StampService stampService;

    public ConverterMediatorImpl(CommentService commentService, StampService stampService) {
        this.commentService = commentService;
        this.stampService = stampService;
    }

    public Comment commentToEntity(CommentDTO commentDTO) {
        return commentService.toEntity(commentDTO);
    }

    public CommentDTO commentToDto (Comment comment) {
        return commentService.toDto(comment);
    }

    public Stamp stampToEntity(StampDTO stampDTO) {
        return stampService.toEntity(stampDTO);
    }

    public StampDTO stampToDto(Stamp stamp) {
        return stampService.toDto(stamp);
    }

    @Override
    public Object toEntity(Object dto) {
        return null;

    }

    @Override
    public Object toDto(Object entity) {
        return null;
    }
}
