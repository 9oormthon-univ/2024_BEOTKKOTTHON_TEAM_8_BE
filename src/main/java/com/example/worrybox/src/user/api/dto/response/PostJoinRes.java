package com.example.worrybox.src.user.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PostJoinRes {
    @ApiModelProperty("유저 아이디")
    Long userId;
    @ApiModelProperty("true (로그인/회원가입 여부 true : 로그인, false : 회원가입)")
    boolean login;

    public PostJoinRes(Long userId, boolean login) {
        this.userId = userId;
        this.login = login;
    }
}
