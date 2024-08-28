package com.jaehyune.stamp_app.service;

import com.jaehyune.stamp_app.dto.StampDTO;
import com.jaehyune.stamp_app.entity.Stamp;

import java.util.List;

/**
 * Service layer for Stamp. Mediates between StampRestController and StampRepository.
 */
public interface StampService extends ConverterMediator<Stamp, StampDTO> {


    /**
     * Save a Stamp.
     * @param dto the DTO of the stamp passed in from the client
     * @return Stamp entity that was saved to database.
     */
    Stamp save(StampDTO dto);

    /**
     * Find a Stamp by its ID. Return its DTO form.
     * @param id id of the Stamp that needs to be found.
     * @return DTO of the Stamp that was found.
     */
    StampDTO findById(Integer id);

    /**
     * Find all Stamps. Return its DTO forms.
     * @return List of DTOs of the Stamps that were found. Return an empty list if else.
     */
    List<StampDTO> findAll();

    /**
     * Delete a Stamp by its id.
     * @param id id of the Stamp that needs to be deleted.
     */
    void delete(Integer id);
}

