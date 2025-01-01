package com.jaehyune.stamp_app.service.impl;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.entity.User;
import com.jaehyune.stamp_app.repository.CommentRepository;
import com.jaehyune.stamp_app.repository.StampRepository;
import com.jaehyune.stamp_app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplUnitTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private StampRepository stampRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void save_comment() {
        // given a comment object
        Integer user_id = 1;
        CommentCreationDTO commentCreationDTO = new CommentCreationDTO("test", null, user_id);
        User user = new User("test", "test@email.com", "123");
        Stamp stamp = new Stamp("test description", 4, "test railway", null);
        Integer stamp_id = 1;
        Comment savedComment = new Comment(stamp, user, null, "test", null, new ArrayList<>());
        savedComment.setId(1);
        // given that the user and stamp exists AND the save and findAll of repository works as functioned
        given(userRepository.findById(user_id)).willReturn(Optional.of(user));
        given(stampRepository.findById(stamp_id)).willReturn(Optional.of(stamp));
        given(commentRepository.save(any(Comment.class))).willReturn(savedComment);
        given(commentRepository.findAll()).willReturn(Collections.singletonList(savedComment));
        // when save is called on the object
        commentService.save(commentCreationDTO,stamp_id);
        // verify it exists in the repository when it is called, and is invoked only once
        verify(commentRepository, times(1)).save(any(Comment.class)); // Ensure save is called once
        assertEquals(1, commentService.findAll().size());
    }

    @Test
    void save_comment_invalid_stamp_id() throws RuntimeException {
        // this one requires less mocking because the exception is caught early and the mock parts are never invoked
        Integer stamp_id = -1;
        Integer user_id = 1;
        CommentCreationDTO commentCreationDTO = new CommentCreationDTO("test", null, user_id);
        // when save is called on the object
        assertThrows(RuntimeException.class, () -> commentService.save(commentCreationDTO, stamp_id));
        // verify it exists in the repository when it is called, and is invoked only once
        verify(commentRepository, never()).save(any(Comment.class)); // Ensure save is called once
        verify(stampRepository, never()).findById(stamp_id);
    }

    @Test
    void save_comment_stamp_id_not_found() {
        Integer stamp_id = 3;
        Integer user_id = 1;
        CommentCreationDTO commentCreationDTO = new CommentCreationDTO("test", null, user_id);
        assertThrows(RuntimeException.class, () -> commentService.save(commentCreationDTO, stamp_id));
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void findById_comment() {
        Integer commentId = 1;
        User user = new User("test", "test@email.com", "123");
        Stamp stamp = new Stamp("test description", 4, "test railway", null);

        Comment comment = new Comment(stamp, user, null, "test", null, new ArrayList<>());
        comment.setId(commentId);
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));
        commentService.findById(commentId);
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void findById_comment_not_found() throws RuntimeException {
        Integer commentId = 1;
        assertThrows(RuntimeException.class, () -> commentService.findById(commentId));
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void findAll_comment() {
        Integer user_id = 1;
        List<Comment> tempList = new ArrayList<>();
        CommentCreationDTO commentCreationDTO = new CommentCreationDTO("test", null, user_id);
        User user = new User("test", "test@email.com", "123");
        Stamp stamp = new Stamp("test description", 4, "test railway", null);
        Integer stamp_id = 1;
        Comment savedComment = new Comment(stamp, user, null, "test", null, new ArrayList<>());
        // given that the user and stamp exists AND the save and findAll of repository works as functioned
        given(userRepository.findById(user_id)).willReturn(Optional.of(user));
        given(stampRepository.findById(stamp_id)).willReturn(Optional.of(stamp));
        given(commentRepository.save(any(Comment.class))).willReturn(savedComment);
        given(commentRepository.findAll()).willReturn(tempList);

        for (int i = 0; i < 3; i++) {
            commentService.save(commentCreationDTO, stamp_id);
            tempList.add(savedComment);
        }
        assertEquals(3, commentService.findAll().size());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void delete_comment() {
        // there is no business logic, so we simply check it is invoking the repository.
        Integer commentId = 1;

        commentService.delete(commentId);
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void delete_comment_invalid_id() throws RuntimeException {
        Integer commentId = -1;

        assertThrows(RuntimeException.class, () -> commentService.delete(commentId));
        verify(commentRepository, times(0)).deleteById(commentId);
    }
}