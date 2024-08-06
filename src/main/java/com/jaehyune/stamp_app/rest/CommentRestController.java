package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.CommentDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentRestController {

    // TODO: On requesting on certain stamp, all associated comments should also be returned
    // TODO: Posting comments to stamps
    // TODO: Users should own comments
    private CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/{id}")
    public CommentDTO findById(@PathVariable Integer id) {
        if (id < 0) throw new IdNotFoundException("Invalid ID: " + id);
        return commentService.findById(id);
    }

    @GetMapping("/comments")
    public List<CommentDTO> findAll() {
        // TODO: Maybe change to return all comment of a specific stamp
        return commentService.findAll();
    }

//    @PostMapping("/comments")
//    public Comment addComment(@RequestBody Comment comment) {
//        comment.setId(0);
//        return commentService.save(comment);
//    }

    @GetMapping("/stamps/{stamp_id}/comments")
    public List<CommentDTO> findAllCommentForStamp(@PathVariable Integer stamp_id) {
        return commentService.findAllCommentForStamp(stamp_id);
    }

    @PostMapping("/stamps/{stamp_id}/comments")
    public Comment addStampComment(@RequestBody CommentDTO comment, @PathVariable Integer stamp_id) {
        comment.setId(0);
        return commentService.save(comment, stamp_id);
    }

    @PutMapping("/stamps/{stamp_id}/comments")
    public Comment editComment(@RequestBody CommentDTO comment, @PathVariable Integer stamp_id) {
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
