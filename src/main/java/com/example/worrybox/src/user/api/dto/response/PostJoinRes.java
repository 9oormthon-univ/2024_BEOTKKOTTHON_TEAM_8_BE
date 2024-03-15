package com.example.worrybox.src.user.api.dto.response;

import lombok.Getter;

@Getter
public class PostJoinRes {
    Long userId;
    boolean login;

    public PostJoinRes(Long userId, boolean login) {
        this.userId = userId;
        this.login = login;
    }
}
