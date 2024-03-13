package com.example.worrybox.src.user.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PostJoinReq {
    @ApiModelProperty("보관함 이름")
    String name;
    @ApiModelProperty("123456 (숫자 6자리)")
    int password;
}
