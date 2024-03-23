package com.example.worrybox.src.cheering.domain.repository;

import com.example.worrybox.src.cheering.domain.CheeringMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheeringMessageRepository extends JpaRepository<CheeringMessage, Long> {
    Page<CheeringMessage> findAll(Pageable pageable);
}
