package com.jaehyune.stamp_app.service.impl;

import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.repository.StampRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StampServiceImplUnitTest {
    @Mock
    private StampRepository stampRepository;
    @InjectMocks
    private StampServiceImpl stampService;

    @Test
    void save_stamp() {
        // assume stamp repository works fine
        // test behaviour
        Integer stamp_id = 1;
        StampDTO stampDTO = new StampDTO(stamp_id, "test", 5, "test rail", null);
        Stamp returnedStamp = new Stamp("test", 5, "test rail", null);
        given(stampRepository.save(any(Stamp.class))).willReturn(returnedStamp);

        stampService.save(stampDTO);

        verify(stampRepository, times(1)).save(any(Stamp.class));
    }

    @Test
    void findById_stamp_success() {
        Integer stamp_id = 1;
        Stamp returnedStamp = new Stamp("test", 5, "test rail", null);
        returnedStamp.setComments(new ArrayList<>());
        given(stampRepository.findById(stamp_id)).willReturn(Optional.of(returnedStamp));

        stampService.findById(stamp_id);
        verify(stampRepository, times(1)).findById(stamp_id);
    }

    @Test
    void findById_stamp_not_found() throws RuntimeException {
        Integer stamp_id = 2;

        assertThrows(RuntimeException.class, () -> stampService.findById(stamp_id));
        verify(stampRepository, times(1)).findById(stamp_id);
    }

    @Test
    void findAll_stamp() {
        stampService.findAll();
        verify(stampRepository, times(1)).findAll();
    }

    @Test
    void delete_stamp() {
        Integer stamp_id = 1;

        stampService.delete(stamp_id);
        verify(stampRepository, times(1)).deleteById(stamp_id);
    }

    @Test
    void delete_stamp_invalid() {
        Integer stamp_id = -1;

        assertThrows(RuntimeException.class, () -> stampService.delete(stamp_id));
        verify(stampRepository, never()).deleteById(stamp_id);
    }
}