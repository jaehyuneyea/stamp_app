package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.entity.Stamp;

import java.util.List;

public interface StampService {

    // create or save a stamp
    Stamp save(Stamp stamp);

    // read a stamp by id
    Stamp findById(Integer id);

    // read all stamps
    List<Stamp> findAll();

    // delete a stamp by id
    void delete(Integer id);
}

