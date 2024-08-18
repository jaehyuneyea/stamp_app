package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Photo;
import com.jaehyune.stamp_app.service.CommentService;
import com.jaehyune.stamp_app.service.PhotoService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    /**
     * Note: This post mapping assumes that the dto of the photo and images are sent in the correct order.
     */
    @PostMapping("/stamps/{stamp_id}/comments")
    public Comment addStampComment(@RequestPart CommentCreationDTO dto,
                                   @PathVariable Integer stamp_id,
                                   @RequestPart Optional<MultipartFile[]> images) {
        // we return comment first so that it can return a valid id
        Comment comment =  commentService.save(dto, stamp_id);
        if (images.isPresent()) {
            // Create a Map that maps all photo metadata that gets passed in as key and every MultipartFile as key
            Map<PhotoDTO, MultipartFile> photoMap = IntStream.range(0, dto.getPhotos().size()).boxed()
                    .collect(Collectors.toMap(i -> dto.getPhotos().get(i), i -> images.get()[i]));

            // TODO: At this point we don't even need to pass in PhotoDTO it would ligten the payload, and we can
            //      initialize it here.
            for (PhotoDTO photoDTO : photoMap.keySet()) {
                photoDTO.setComment_id(comment.getId());
            }
            List<Photo> photoList = new ArrayList<>();
            photoMap.forEach((k,v) -> {
                photoList.add(photoService.save(k, v));
            });
            comment.setPhotos(photoList);
        }
        return comment;
    }

    // TODO: Handle images for editting comments as well
    @PutMapping("/stamps/{stamp_id}/comments")
    public Comment editComment(@RequestBody CommentCreationDTO comment, @PathVariable Integer stamp_id) {
        return commentService.save(comment, stamp_id);
    }

    /**
     * This method should delete the comment and all its associated photos.
     */
    @DeleteMapping("/comments/{id}")
    public void delete(@PathVariable Integer id) {
        if (id < 0) {
            throw new IdNotFoundException("Invalid ID: " + id);
        }
        CommentReadDTO comment = commentService.findById(id);
        List<PhotoDTO> photoDTOS = comment.getPhotos();
        if (!photoDTOS.isEmpty()) {
            for (PhotoDTO photoDTO: photoDTOS) {
                String photoId = photoDTO.getId();
                photoService.delete(photoId);
            }
        }
        commentService.delete(id);
    }
}
