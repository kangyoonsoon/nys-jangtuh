package com.mire.nysjangtuh.repository;

import com.mire.nysjangtuh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
