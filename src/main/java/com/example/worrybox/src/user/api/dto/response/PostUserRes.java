package com.example.worrybox.src.user.api.dto.response;

import lombok.Getter;

@Getter
public class PostUserRes {
    Long userId;

    public PostUserRes(Long userId) {
        this.userId = userId;
    }
}
