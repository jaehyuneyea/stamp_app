package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Photo;
import com.jaehyune.stamp_app.service.CommentService;
import com.jaehyune.stamp_app.service.PhotoService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentRestController {

    private CommentService commentService;
    private PhotoService photoService;

    public CommentRestController(CommentService commentService, PhotoService photoService) {
        this.commentService = commentService;
        this.photoService = photoService;
    }

    @GetMapping("/comments/{id}")
    public CommentReadDTO findById(@PathVariable Integer id) {
        if (id < 0) throw new IdNotFoundException("Invalid ID: " + id);
        return commentService.findById(id);
    }

    @GetMapping("/comments")
    public List<CommentReadDTO> findAll() {
        // TODO: Maybe change to return all comment of a specific stamp
        return commentService.findAll();
    }

//    @PostMapping("/comments")
//    public Comment addComment(@RequestBody Comment comment) {
//        comment.setId(0);
//        return commentService.save(comment);
//    }

    @GetMapping("/stamps/{stamp_id}/comments")
    public List<CommentReadDTO> findAllCommentForStamp(@PathVariable Integer stamp_id) {
        return commentService.findAllCommentForStamp(stamp_id);
    }

    @PostMapping("/stamps/{stamp_id}/comments")
    public Comment addStampComment(@RequestPart CommentCreationDTO dto,
                                   @PathVariable Integer stamp_id,
                                   @RequestPart MultipartFile image) {
        // we return comment first so that it can return a valid id
        Comment comment =  commentService.save(dto, stamp_id);
        dto.getPhotos().get(0).setComment_id(comment.getId());
        Photo photo = photoService.save(dto.getPhotos().get(0), image);
        List<Photo> photoList = new ArrayList<>();
        photoList.add(photo);
        comment.setPhotos(photoList);
        return comment;
    }

    @PutMapping("/stamps/{stamp_id}/comments")
    public Comment editComment(@RequestBody CommentCreationDTO comment, @PathVariable Integer stamp_id) {
        return commentService.save(comment, stamp_id);
    }

    @DeleteMapping("/comments/{id}")
    public void delete(@PathVariable Integer id) {
        if (id < 0) {
            throw new IdNotFoundException("Invalid ID: " + id);
        }
        commentService.delete(id);
    }
}
