package com.example.worrybox.src.memo.domain.repository;

import com.example.worrybox.src.memo.domain.Memo;
import com.example.worrybox.src.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo,Long> {
    List<Memo> findByUserId(Long userId);

    List<Memo> findByUpdatedAtBefore(Timestamp dateTime);

}
