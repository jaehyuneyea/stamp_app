package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.repository.StampRepository;

import java.util.List;
import java.util.Optional;

public class StampServiceImpl implements StampService {

    private StampRepository stampRepository;

    public StampServiceImpl(StampRepository stampRepository) {
        this.stampRepository = stampRepository;
    }
    @Override
    public Stamp save(Stamp stamp) {
        return stampRepository.save(stamp);
    }

    @Override
    public Stamp findById(Integer id) {
        Optional<Stamp> tempStamp = stampRepository.findById(id);
        if (tempStamp.isPresent()) {
            return tempStamp.get();
        } else {
            throw new RuntimeException("Did not find Stamp with ID: " + id);
        }
    }

    @Override
    public List<Stamp> findAll() {
        return stampRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        stampRepository.deleteById(id);
    }
}
