package com.example.worrybox.src.user.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostLoginReq {
    @NotBlank(message="이름을 입력해야 합니다.")
    String name;
    @Min(value = 100000, message = "비밀번호는 6자리여야 합니다.")
    @Max(value = 999999, message = "비밀번호는 6자리여야 합니다.")
    int password;
}
