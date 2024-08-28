package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Photo;
import com.jaehyune.stamp_app.rest.error.IdNotFoundException;
import com.jaehyune.stamp_app.service.CommentService;
import com.jaehyune.stamp_app.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The Rest Controller for Comments. This handles creating, updating, deleting and reading comments.
 * It delegates the work to CommentService, and any photo persistence to PhotoService.
 *
 * Comments are associated to a Stamp, therefore the proper HTTP requests for it is
 * GET /stamps/{stamp_id}/comments
 *
 * unless it is for deleting the stamp.
 */

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
     * @param dto the CommentCreationDTO which is passed in the body of the JSON from the client side.
     * @param stamp_id id of the stamp to which the comment will be created; exists in the URL path.
     * @param images any image files that are being passed in with the comment. It is marked as optional because the user
     *               can choose not to pass in any images to their comment.
     * @return the completed Comment object with any photo metadata associated to the comment
     */
    @PostMapping("/stamps/{stamp_id}/comments")
    public Comment addStampComment(@RequestPart CommentCreationDTO dto,
                                   @PathVariable Integer stamp_id,
                                   @RequestPart Optional<MultipartFile[]> images) {
        // we return comment first so that it can return a valid id
        Comment comment =  commentService.save(dto, stamp_id);
        if (images.isPresent()) {

            // Populate a list of PhotoDTOs with corresponding comment ID with length of images passed in
            List<PhotoDTO> dtos = new ArrayList<>();
            for (int i = 0; i <  images.get().length; i++) {
                PhotoDTO tempDTO = new PhotoDTO();
                tempDTO.setStamp_id(null);
                tempDTO.setComment_id(comment.getId());
                dtos.add(tempDTO);
            }
            // Create a Map that maps all photo metadata to be created that gets passed in as key and every MultipartFile as key
            Map<PhotoDTO, MultipartFile> photoMap = IntStream.range(0, dtos.size()).boxed()
                    .collect(Collectors.toMap(i -> dtos.get(i), i -> images.get()[i]));

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
