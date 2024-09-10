package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.entity.Comment;

import java.util.List;

/**
 * Service layer for Comment. Mediates between CommentRestController and CommentRepository.
 */
public interface CommentService extends ConverterMediator<Comment, CommentReadDTO> {

    /**
     *
     * @param dto CommentCreationDTO passed in from client side as JSON body. Contains description of the comment
     *            and the id of the user that made the comment.
     * @param stamp_id id of the stamp that the comment is being written to.
     * @return a Comment object that represents the entity that was stored in the database.
     */
    Comment save(CommentCreationDTO dto, Integer stamp_id);

    /**
     *
     * @param id id of the comment that needs to be found
     * @return the DTO of the found Comment
     */
    CommentReadDTO findById(Integer id);

    /**
     * Find all Comments
     * @return a list of all Comment DTOs. Return an empty list if else.
     */
    List<CommentReadDTO> findAll();

    /**
     * Find Comments for a specific Stamp.
     * @param stamp_id id of the stamp that the comments need to be fond for
     * @return all Comments associated with the stamp
     */
    List<CommentReadDTO> findAllCommentForStamp(Integer stamp_id);

    /**
     * Delete of Comment with a corresponding id.
     * @param id id of the Comment that will be deleted
     */
    void delete(Integer id);
}
