package com.jaehyune.stamp_app.repository;

import com.jaehyune.stamp_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
