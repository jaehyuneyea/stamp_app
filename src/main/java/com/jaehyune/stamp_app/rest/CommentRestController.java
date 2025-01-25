package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.dto.CommentCreationDTO;
import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Photo;
import com.jaehyune.stamp_app.rest.error.IdNotFoundException;
import com.jaehyune.stamp_app.service.CommentService;
import com.jaehyune.stamp_app.service.PhotoService;
import com.jaehyune.stamp_app.service.UserService;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
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
    private UserService userService;

    public CommentRestController(CommentService commentService, PhotoService photoService, UserService userService) {
        this.commentService = commentService;
        this.photoService = photoService;
        this.userService = userService;
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
                PhotoDTO tempDTO = PhotoDTO.builder().build();
                tempDTO.setStampId(null);
                tempDTO.setCommentId(comment.getId());
                dtos.add(tempDTO);
            }
            // Create a Map that maps all photo metadata to be created that gets passed in as key and every MultipartFile as key
            Map<PhotoDTO, MultipartFile> photoMap = IntStream.range(0, dtos.size()).boxed()
                    .collect(Collectors.toMap(dtos::get, i -> images.get()[i]));

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
    public Comment editComment(@RequestPart CommentCreationDTO dto,
                               @PathVariable Integer stamp_id,
                               @RequestPart Optional<MultipartFile[]> images) {
        Comment comment = commentService.save(dto, stamp_id);
        return comment; // styb
    }
    // we assume that any images in the images field are images explicitly meant to be added which is to be
    // handled by the client.
    @PatchMapping("stamps/{stamp_id}/comments/{comment_id}")
    public Comment editComment(@RequestPart Map<String, Object> fields,
                               @PathVariable Integer stamp_id,
                               @PathVariable Integer comment_id,
                               @RequestPart Optional<MultipartFile[]> images) {
        // a request body for handling comment definition
        CommentReadDTO commentReadDTO = commentService.findById(comment_id);
        if (commentReadDTO == null) {
            throw new RuntimeException("Comment not found");
        }
        fields.forEach((k,v) -> {
            if (k.equals("delete")) {
                // we're casting / expecting v to be a list of keys to delete
                if (v instanceof List<?>) {
                    try {
                        List<String> deleteList = (List<String>) v;
                        // check to see if all the keys are valid before deleting any images first
                        for (String s : deleteList) {
                            photoService.findById(s);
                        }
                        for (String s : deleteList) {
                            photoService.delete(s);
                        }
                    } catch (RuntimeException e) {
                        throw new RuntimeException("Unexpected Error Occurred");
                    }
                }
            } else {
                Field field = ReflectionUtils.findField(CommentReadDTO.class, k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, commentReadDTO, v);
            }
        });
        CommentCreationDTO commentCreationDTO = CommentCreationDTO.builder()
                .id(comment_id)
                .userId(userService.findByName(commentReadDTO.getUsername()))
                .parentId(commentReadDTO.getParentId())
                .description(commentReadDTO.getDescription())
                .build();
        Comment comment = commentService.save(commentCreationDTO, stamp_id);
        // image handling
        List<Photo> photos = new ArrayList<>(commentReadDTO.getPhotoDTOs()
                .stream()
                .map(photoService::toEntity)
                .toList());
        if (images.isPresent()) {
            for (MultipartFile image : images.get()) {
                PhotoDTO photoDTO = PhotoDTO.builder()
                        .commentId(comment.getId())
                        .build();
                photos.add(photoService.save(photoDTO, image));
            }
        comment.setPhotos(photos);
        }
        return comment;
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
        List<PhotoDTO> photoDTOS = comment.getPhotoDTOs();
        if (!photoDTOS.isEmpty()) {
            for (PhotoDTO photoDTO: photoDTOS) {
                String photoId = photoDTO.getId();
                photoService.delete(photoId);
            }
        }
        commentService.delete(id);
    }
}
