package com.jaehyune.stamp_app.repository;

import com.jaehyune.stamp_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository layer for User to persist, read or delete from the database which extends JpaRepository.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
