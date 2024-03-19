package com.example.worrybox.src.letter.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PostLetterRes {
    @Schema(description = "편지 아이디", example = "1")
    Long letterId;

    public PostLetterRes(Long letterId) {
        this.letterId = letterId;
    }
}
