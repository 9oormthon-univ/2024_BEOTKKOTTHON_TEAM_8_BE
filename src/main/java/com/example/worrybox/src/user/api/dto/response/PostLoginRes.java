package com.example.worrybox.src.user.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PostLoginRes {
    @Schema(description = "유저 아이디", example = "1")
    Long userId;
    @Schema(description = "걱정 시작 시간", example = "11:00")
    String startTime;
    @Schema(description = "걱정 종료 시간", example = "12:00")
    String endTime;

    public PostLoginRes(Long userId, String startTime, String endTime) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
