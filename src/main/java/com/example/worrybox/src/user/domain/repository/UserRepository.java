package com.example.worrybox.src.user.domain.repository;

import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameAndStatus(String name, Status status);
    Optional<User> findByIdAndStatus(Long id, Status status);
    Optional<User> findByPasswordAndStatus(int password, Status status);
    Optional<User> findByNameAndPasswordAndStatus(String name, int password, Status status);
}
