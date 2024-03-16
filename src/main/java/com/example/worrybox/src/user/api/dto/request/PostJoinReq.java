package com.example.worrybox.src.user.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PostJoinReq {
    @NotBlank(message="이름을 입력해야 합니다.")
    String name;
    @Min(value = 100000, message = "비밀번호는 6자리여야 합니다.")
    @Max(value = 999999, message = "비밀번호는 6자리여야 합니다.")
    int password;
    @NotBlank(message="걱정 시작 시간을 입력해야 합니다.")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "걱정 시간 형식은 0:00입니다")
    String startTime;
    @NotBlank(message="걱정 종료 시간을 입력해야 합니다.")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "걱정 시간 형식은 0:00입니다")
    String endTime;
}
