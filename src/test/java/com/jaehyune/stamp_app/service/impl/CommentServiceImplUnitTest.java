package com.jaehyune.stamp_app.service.impl;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.entity.User;
import com.jaehyune.stamp_app.repository.CommentRepository;
import com.jaehyune.stamp_app.repository.StampRepository;
import com.jaehyune.stamp_app.repository.UserRepository;
import com.jaehyune.stamp_app.service.CommentService;
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
        CommentCreationDTO commentCreationDTO = CommentCreationDTO.builder()
                .description("test")
                .parentId(null)
                .userId(user_id)
                .build();
        User user = User.builder()
                .username("test")
                .email("test@email.com")
                .password("123")
                .build();
        Stamp stamp = Stamp.builder()
                .description("test description")
                .rating(4)
                .railway("test railway")
                .build();
        Integer stamp_id = 1;
        Comment savedComment = Comment.builder()
                .stampId(stamp)
                .userId(user)
                .description("test")
                .photos(new ArrayList<>())
                .build();
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
        CommentCreationDTO commentCreationDTO = CommentCreationDTO.builder()
                .description("test")
                .parentId(null)
                .userId(user_id)
                .build();
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
        CommentCreationDTO commentCreationDTO = CommentCreationDTO.builder()
                .description("test")
                .parentId(null)
                .userId(user_id)
                .build();
        assertThrows(RuntimeException.class, () -> commentService.save(commentCreationDTO, stamp_id));
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void findById_comment() {
        Integer commentId = 1;
        User user = User.builder()
                .username("test")
                .email("test@email.com")
                .password("123")
                .build();
        Stamp stamp = Stamp.builder()
                .description("test description")
                .rating(4)
                .railway("test railway")
                .build();

        Comment comment = Comment.builder()
                .stampId(stamp)
                .userId(user)
                .description("test")
                .photos(new ArrayList<>())
                .id(commentId)
                .build();
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));
        commentService.findById(commentId);
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void findById_comment_invalid() {
        Integer comment_id = -1;
        assertThrows(RuntimeException.class, () -> commentService.findById(comment_id));
        verify(commentRepository, never()).findById(comment_id);
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
        CommentCreationDTO commentCreationDTO = CommentCreationDTO.builder()
                .description("test")
                .parentId(null)
                .userId(user_id)
                .build();
        User user = User.builder()
                .username("test")
                .email("test@email.com")
                .password("123")
                .build();
        Stamp stamp = Stamp.builder()
                .description("test description")
                .rating(4)
                .railway("test railway")
                .build();
        Integer stamp_id = 1;
        Comment savedComment = Comment.builder()
                .stampId(stamp)
                .userId(user)
                .description("test")
                .photos(new ArrayList<>())
                .build();        // given that the user and stamp exists AND the save and findAll of repository works as functioned
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
    void findAll_comment_for_stamp_invalid() {
        Integer stamp_id = -1;
        assertThrows(RuntimeException.class, () -> commentService.findAllCommentForStamp(stamp_id));
        verify(stampRepository, never()).findById(stamp_id);
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