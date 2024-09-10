package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.dto.UserCreationDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.service.CommentService;
import com.jaehyune.stamp_app.service.PhotoService;
import com.jaehyune.stamp_app.service.StampService;
import com.jaehyune.stamp_app.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * An integration test for CommentRestController.
 * TODO: Add more exception testing
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestExecutionListeners({DirtiesContextTestExecutionListener.class})
class CommentRestControllerIntegrationTest {

    private CommentService commentService;
    private PhotoService photoService;
    private UserService userService;
    private StampService stampService;

    private CommentCreationDTO commentCreationDTO;
    private StampDTO stampDTO;

    @Autowired
    public CommentRestControllerIntegrationTest(CommentService commentService, PhotoService photoService, UserService userService, StampService stampService) {
        this.commentService = commentService;
        this.photoService = photoService;
        this.userService = userService;
        this.stampService = stampService;
    }

    @BeforeEach
    public void setup() {
        stampDTO = new StampDTO();
        stampDTO.setId(0);
        stampDTO.setDescription("test railway");
        stampDTO.setRailway("test lines");
        stampDTO.setRating(4);
        List<CommentReadDTO> listDTO = new ArrayList<>();
        stampDTO.setComments(listDTO);
        stampDTO.setPhoto(null);

        stampService.save(stampDTO);

        UserCreationDTO userCreationDTO = new UserCreationDTO("testuser", "abc@email.com", "test123");
        userService.createUser(userCreationDTO);

        commentCreationDTO = new CommentCreationDTO();
        commentCreationDTO.setDescription("test comment");
        commentCreationDTO.setParent_id(null);
        commentCreationDTO.setUser_id(1);
    }

    @Test
    void add_comment_to_stamp_then_find_by_comment_id() {
        Comment comment = commentService.save(commentCreationDTO, 1);
        Assertions.assertEquals(1, comment.getId());

        CommentReadDTO foundComment = commentService.findById(comment.getId());
        Assertions.assertEquals("testuser", foundComment.getUsername());
        Assertions.assertEquals("test comment", foundComment.getDescription());
        Assertions.assertEquals(1, foundComment.getId());
        assertNull(foundComment.getParent_id());
        Assertions.assertEquals(new ArrayList<>(), foundComment.getPhotos());
        Assertions.assertEquals(comment.getDate_created(), foundComment.getDate_created());

    }

    @Test
    void add_comment_to_stamp_then_find_by_stamp() {
        Comment comment = commentService.save(commentCreationDTO, 1);
        Assertions.assertEquals(1, comment.getId());

        StampDTO foundStamp = stampService.findById(1);
        Assertions.assertEquals(1, foundStamp.getComments().size());
        CommentReadDTO commentReadDTO =  foundStamp.getComments().get(0);
        Assertions.assertEquals("testuser", commentReadDTO.getUsername());
        Assertions.assertEquals("test comment", commentReadDTO.getDescription());
        Assertions.assertEquals(1, commentReadDTO.getId());
        assertNull(commentReadDTO.getParent_id());
        Assertions.assertEquals(new ArrayList<>(), commentReadDTO.getPhotos());
        Assertions.assertEquals(comment.getDate_created(), commentReadDTO.getDate_created());
    }

    @Test
    void add_comment_to_stamp_then_edit_then_find_by_id() {
        Comment comment = commentService.save(commentCreationDTO, 1);
        Assertions.assertEquals(1, comment.getId());

        CommentCreationDTO newDTO = new CommentCreationDTO();
        newDTO.setId(1);
        newDTO.setDescription("new comment");
        newDTO.setParent_id(null);
        newDTO.setUser_id(1);

        commentService.save(newDTO, 1);

        CommentReadDTO updatedComment = commentService.findById(1);
        Assertions.assertEquals("new comment", updatedComment.getDescription());
    }

    @Test
    void add_three_comments_to_stamp_then_find_all() {
        Comment comment1 = commentService.save(commentCreationDTO, 1);
        Comment comment2 = commentService.save(commentCreationDTO, 1);
        Comment comment3 = commentService.save(commentCreationDTO, 1);

        Assertions.assertEquals(1, comment1.getId());
        Assertions.assertEquals(2, comment2.getId());
        Assertions.assertEquals(3, comment3.getId());

        List<CommentReadDTO> dtos = commentService.findAll();
        Assertions.assertEquals(3, dtos.size());
        for(CommentReadDTO dto: dtos) {
            Assertions.assertEquals(commentCreationDTO.getDescription(), dto.getDescription());
            Assertions.assertNull(dto.getParent_id());
            Assertions.assertEquals(userService.findById(commentCreationDTO.getUser_id()).getUsername(), dto.getUsername());
        }
    }

    @Test
    void add_two_comments_to_one_stamp_and_one_comment_to_another_and_findall_by_stamp() {
        // adds the second stamp with id 2
        Stamp dto = stampService.save(stampDTO);

        Assertions.assertEquals(2, dto.getId());

        Comment comment1 = commentService.save(commentCreationDTO, 1);
        Comment comment2 = commentService.save(commentCreationDTO, 1);
        Comment comment3 = commentService.save(commentCreationDTO, 2);

        Assertions.assertEquals(1, comment1.getId());
        Assertions.assertEquals(2, comment2.getId());
        Assertions.assertEquals(3, comment3.getId());

        List<CommentReadDTO> commentDtos1 = commentService.findAllCommentForStamp(1);
        List<CommentReadDTO> commentDtos2 = commentService.findAllCommentForStamp(2);
        Assertions.assertEquals(2, commentDtos1.size());
        Assertions.assertEquals(1, commentDtos2.size());
    }

    @Test
    void add_comment_to_stamp_then_delete() throws RuntimeException {
        Comment comment = commentService.save(commentCreationDTO, 1);
        Assertions.assertEquals(1, comment.getId());

        commentService.delete(comment.getId());
        Assertions.assertThrows(RuntimeException.class, () -> commentService.findById(comment.getId()));
    }
}