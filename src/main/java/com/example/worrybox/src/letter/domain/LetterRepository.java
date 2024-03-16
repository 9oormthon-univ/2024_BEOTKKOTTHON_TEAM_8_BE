package com.example.worrybox.src.letter.domain;

import com.example.worrybox.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface LetterRepository  extends JpaRepository<Letter, Long>  {
    List<Letter> findAllByUserIdAndStatus(Long userId, Status status);
}
