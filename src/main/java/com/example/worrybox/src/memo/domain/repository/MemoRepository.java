package com.example.worrybox.src.memo.domain.repository;

import com.example.worrybox.src.memo.domain.Memo;
import com.example.worrybox.src.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo,Long> {
    Optional<Memo> findByUser(User user);

    List<Memo> findByUserId(Long userId);
}
