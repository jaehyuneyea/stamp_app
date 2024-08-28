package com.jaehyune.stamp_app.repository;

import com.jaehyune.stamp_app.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository layer for Comment to persist, read or delete from the database which extends JpaRepository.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
