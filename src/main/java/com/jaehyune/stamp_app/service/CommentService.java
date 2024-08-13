package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.entity.Comment;

import java.util.List;

public interface CommentService extends ConverterMediator<Comment, CommentReadDTO> {

    Comment save(CommentCreationDTO dto, Integer stamp_id);

    CommentReadDTO findById(Integer id);

    List<CommentReadDTO> findAll();

    List<CommentReadDTO> findAllCommentForStamp(Integer stamp_id);

    void delete(Integer id);
}
