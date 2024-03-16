package com.example.worrybox.src.letter.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface LetterRepository  extends JpaRepository<Letter, Long>  {
}
