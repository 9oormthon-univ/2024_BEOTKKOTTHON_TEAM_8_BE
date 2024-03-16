package com.example.worrybox.src.letter.api;

import com.example.worrybox.src.letter.application.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letters")
public class LetterController {
    private final LetterService letterService;


}
