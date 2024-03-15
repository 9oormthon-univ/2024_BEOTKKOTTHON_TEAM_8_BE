package com.example.worrybox.src.user.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PostUserRes {
    @ApiModelProperty("1")
    Long userId;

    public PostUserRes(Long userId) {
        this.userId = userId;
    }
}
