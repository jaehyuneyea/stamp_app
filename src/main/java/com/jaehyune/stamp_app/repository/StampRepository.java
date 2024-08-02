package com.jaehyune.stamp_app.repository;

import com.jaehyune.stamp_app.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampRepository extends JpaRepository<Stamp, Integer> {
}
