package com.jaehyune.stamp_app.service.impl;

import com.jaehyune.stamp_app.dto.CommentReadDTO;
import com.jaehyune.stamp_app.dto.PhotoDTO;
import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Photo;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.repository.StampRepository;
import com.jaehyune.stamp_app.service.ConverterMediator;
import com.jaehyune.stamp_app.service.StampService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StampServiceImpl implements StampService {
    private StampRepository stampRepository;
    private ConverterMediator<Comment, CommentReadDTO> commentConverter;
    private ConverterMediator<Photo, PhotoDTO> photoConverter;

    public StampServiceImpl(StampRepository stampRepository, ConverterMediator<Comment, CommentReadDTO> commentConverter, ConverterMediator<Photo, PhotoDTO> photoConverter) {
        this.stampRepository = stampRepository;
        this.commentConverter = commentConverter;
        this.photoConverter = photoConverter;
    }

    @Override
    public Stamp save(StampDTO dto) {
        Stamp stamp = toEntity(dto);
        return stampRepository.save(stamp);
    }

    @Transactional
    @Override
    public StampDTO findById(Integer id) {
        Optional<Stamp> tempStamp = stampRepository.findById(id);
        if (tempStamp.isPresent()) {
            return toDto(tempStamp.get());
        } else {
            throw new RuntimeException("Did not find Stamp with ID: " + id);
        }
    }

    @Transactional
    @Override
    public List<StampDTO> findAll() {
        List<Stamp> stamps = stampRepository.findAll();
        return stamps.stream().map(this::toDto).toList();
    }

    @Override
    public void delete(Integer id) {
        stampRepository.deleteById(id);
    }

    @Override
    public Stamp toEntity(StampDTO dto) {
        return Stamp.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .rating(dto.getRating())
                .railway(dto.getRailway())
                .comments(dto.getComments() != null ?
                        dto.getComments()
                                .stream()
                                .map(commentDTO -> commentConverter.toEntity(commentDTO)).toList()
                        : Collections.emptyList())
                .build();
    }

    @Override
    public StampDTO toDto(Stamp stamp) {
        return StampDTO.builder()
                .description(stamp.getDescription())
                .railway(stamp.getRailway())
                .id(stamp.getId())
                .rating(stamp.getRating())
                .comments(stamp.getComments() != null ?
                        stamp.getComments()
                                .stream()
                                .map(comment -> commentConverter.toDto(comment)).toList()
                        : Collections.emptyList())
                .photo(stamp.getPhoto() != null ?
                        photoConverter.toDto(stamp.getPhoto())
                        : null)
                .build();
    }
}
