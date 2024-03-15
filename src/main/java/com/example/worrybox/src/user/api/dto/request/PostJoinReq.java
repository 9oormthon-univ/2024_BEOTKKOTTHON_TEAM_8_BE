package com.example.worrybox.src.user.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostJoinReq {
    @NotBlank(message="이름을 입력해야 합니다.")
    String name;
    @NotBlank(message="비밀번호를 입력해야 합니다.")
    int password;
}
