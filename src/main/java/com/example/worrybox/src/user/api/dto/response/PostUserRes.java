package com.example.worrybox.src.user.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PostUserRes {
    @Schema(description = "유저 아이디", example = "1")
    Long userId;

    public PostUserRes(Long userId) {
        this.userId = userId;
    }
}
