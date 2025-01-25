package com.jaehyune.stamp_app.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.entity.User;
import com.jaehyune.stamp_app.service.CommentService;
import com.jaehyune.stamp_app.service.PhotoService;
import com.jaehyune.stamp_app.service.StampService;
import com.jaehyune.stamp_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentRestController.class)
class CommentRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;
    @MockBean
    private PhotoService photoService;
    @MockBean
    private UserService userService;
    @MockBean
    private StampService stampService;

    @Test
    void findByIdTest() throws Exception {
        when(commentService.findById(1))
                .thenReturn(CommentReadDTO.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/comments/1"))
                .andExpect(status().isOk());
    }
// TODO: figure out how to work with rest controller testing, particularly multipart form data posting.

//    @Test
//    void addStampCommentTest() throws Exception {
//        CommentCreationDTO dto = CommentCreationDTO.builder()
//                .userId(1)
//                .description("test")
//                .parentId(null)
//                .build();
//        mockMvc.perform(multipart("/stamps/1/comments")
//                        .param("dto", toJson(dto)))
//                        .andExpect(status().isOk());
//    }

    private String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}