package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.CommentDTO;
import com.jaehyune.stamp_app.entity.Comment;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CommentService extends ConverterMediator<Comment, CommentDTO> {

    Comment save(CommentDTO dto, Integer stamp_id);

    CommentDTO findById(Integer id);

    List<CommentDTO> findAll();

    List<CommentDTO> findAllCommentForStamp(Integer stamp_id);

    void delete(Integer id);
}
