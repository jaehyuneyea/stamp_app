package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment save(Comment comment);

    Comment findById(Integer id);

    List<Comment> findAll();

    void delete(Integer id);
}
