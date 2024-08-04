package com.jaehyune.stamp_app.repository;

import com.jaehyune.stamp_app.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
