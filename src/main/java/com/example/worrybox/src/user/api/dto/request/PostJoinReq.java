package com.example.worrybox.src.user.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PostJoinReq {
    @ApiModelProperty("OO의 보관함")
    String name;
    @ApiModelProperty("123456 (숫자 6자리)")
    int password;
}
