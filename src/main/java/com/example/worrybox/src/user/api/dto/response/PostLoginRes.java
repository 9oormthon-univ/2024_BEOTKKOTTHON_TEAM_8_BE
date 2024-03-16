package com.example.worrybox.src.user.api.dto.response;

import lombok.Getter;

@Getter
public class PostLoginRes {
    Long userId;
    String startTime;
    String endTime;

    public PostLoginRes(Long userId, String startTime, String endTime) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
