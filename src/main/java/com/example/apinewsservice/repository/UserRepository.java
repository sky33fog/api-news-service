package com.example.apinewsservice.repository;

import com.example.apinewsservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
