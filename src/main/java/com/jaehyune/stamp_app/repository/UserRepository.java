package com.jaehyune.stamp_app.repository;

import com.jaehyune.stamp_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * The repository layer for User to persist, read or delete from the database which extends JpaRepository.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u.id FROM users u WHERE u.username = :user", nativeQuery = true)
    Integer findByName(@Param("user")String user);
}
