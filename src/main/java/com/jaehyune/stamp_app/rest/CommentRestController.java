package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentRestController {

    private CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
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
    public Comment addStampComment(@RequestBody CommentCreationDTO comment, @PathVariable Integer stamp_id) {
        return commentService.save(comment, stamp_id);
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
