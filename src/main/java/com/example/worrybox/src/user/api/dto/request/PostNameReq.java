package com.example.worrybox.src.user.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostNameReq {
    @NotBlank(message="이름을 입력해야 합니다.")
    String name;
}
