package com.example.worrybox.src.user.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostJoinReq {
    @NotBlank(message="이름을 입력해야 합니다.")
    @ApiModelProperty("OO의 보관함")
    String name;
    @NotBlank(message="비밀번호를 입력해야 합니다.")
    @ApiModelProperty("123456 (숫자 6자리)")
    int password;
}
