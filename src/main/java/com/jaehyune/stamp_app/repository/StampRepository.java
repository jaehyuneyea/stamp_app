package com.jaehyune.stamp_app.repository;

import com.jaehyune.stamp_app.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository layer for Stamp to persist, read or delete from the database which extends JpaRepository.
 */
public interface StampRepository extends JpaRepository<Stamp, Integer> {
}
