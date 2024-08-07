package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.entity.Stamp;

import java.util.List;

public interface StampService extends ConverterMediator<Stamp, StampDTO> {


    // create or save a stamp
    Stamp save(StampDTO dto);

    // read a stamp by id
    StampDTO findById(Integer id);

    // read all stamps
    List<StampDTO> findAll();

    // delete a stamp by id
    void delete(Integer id);
}

