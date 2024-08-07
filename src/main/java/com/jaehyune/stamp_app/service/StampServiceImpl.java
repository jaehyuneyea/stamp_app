package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.CommentDTO;
import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.entity.Comment;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.repository.StampRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StampServiceImpl implements StampService {
    private StampRepository stampRepository;
    private ConverterMediator<Comment, CommentDTO> converterMediator; // this seems very bad

    public StampServiceImpl(StampRepository stampRepository, ConverterMediator<Comment, CommentDTO> converterMediator) {
        this.stampRepository = stampRepository;
        this.converterMediator = converterMediator;
    }

    @Override
    public Stamp save(StampDTO dto) {
        Stamp stamp = toEntity(dto);
        return stampRepository.save(stamp);
    }

    @Override
    public StampDTO findById(Integer id) {
        Optional<Stamp> tempStamp = stampRepository.findById(id);
        if (tempStamp.isPresent()) {
            return toDto(tempStamp.get());
        } else {
            throw new RuntimeException("Did not find Stamp with ID: " + id);
        }
    }

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
        Stamp stamp = new Stamp();
        stamp.setId(dto.getId());
        stamp.setDescription(dto.getDescription());
        stamp.setRating(dto.getRating());
        stamp.setRailway(dto.getRailway());

        List<CommentDTO> commentDTOS = dto.getComments();

        stamp.setComments(commentDTOS.stream().map(commentDTO -> converterMediator.toEntity(commentDTO)).toList());

        return stamp;
    }

    @Override
    public StampDTO toDto(Stamp stamp) {
        StampDTO dto = new StampDTO();
        dto.setDescription(stamp.getDescription());
        dto.setRailway(stamp.getRailway());
        dto.setId(stamp.getId());
        dto.setRating(stamp.getRating());

        List<Comment> comments = stamp.getComments();
        dto.setComments(comments.stream().map(comment -> converterMediator.toDto(comment)).toList());

        return dto;
    }
}
