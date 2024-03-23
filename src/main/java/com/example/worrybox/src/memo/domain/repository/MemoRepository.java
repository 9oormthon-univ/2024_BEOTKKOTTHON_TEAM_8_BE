package com.example.worrybox.src.memo.domain.repository;

import com.example.worrybox.src.memo.domain.Memo;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo,Long> {
    List<Memo> findByUserId(Long userId);

    List<Memo> findByUpdatedAtBeforeAndStatusAndUser(Timestamp dateTime, Status status, User user);

}
