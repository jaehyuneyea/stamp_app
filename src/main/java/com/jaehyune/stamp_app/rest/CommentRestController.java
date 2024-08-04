package com.jaehyune.stamp_app.rest;

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
    public Comment findById(@PathVariable Integer id) {
        if (id < 0) throw new IdNotFoundException("Invalid ID: " + id);
        return commentService.findById(id);
    }

    @GetMapping("/comments")
    public List<Comment> findAll() {
        // TODO: Maybe change to return all comment of a specific stamp
        return commentService.findAll();
    }

    @PostMapping("/comments")
    public Comment addComment(@RequestBody Comment comment) {
        comment.setId(0);
        return commentService.save(comment);
    }

    @PutMapping("/comments")
    public Comment editComment(@RequestBody Comment comment) {
        return commentService.save(comment);
    }

    @DeleteMapping("/comments/{id}")
    public void delete(@PathVariable Integer id) {
        if (id < 0) {
            throw new IdNotFoundException("Invalid ID: " + id);
        }
        commentService.delete(id);
    }
}
