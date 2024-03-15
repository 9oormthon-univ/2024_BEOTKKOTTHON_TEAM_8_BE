package com.example.worrybox.src.letter.application;

import com.example.worrybox.src.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LetterService {
    private final UserRepository userRepository;
}
