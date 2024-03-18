package com.example.worrybox.src.user.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class PostJoinReq {
    @NotBlank(message="이름을 입력해야 합니다.")
    @Size(min = 1, max = 5, message = "이름은 한글자 이상, 다섯글자 이하입니다.")
    @Schema(description = "보관함 이름", example = "ㅇㅇ의 보관함")
    String name;
    @Min(value = 1000, message = "비밀번호는 4자리여야 합니다.")
    @Max(value = 9999, message = "비밀번호는 4자리여야 합니다.")
    @Schema(description = "보관함 비밀번호", example = "1234")
    int password;
    @NotBlank(message="걱정 시작 시간을 입력해야 합니다.")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "걱정 시간 형식은 0:00입니다")
    @Schema(description = "걱정 시작 시간", example = "11:00")
    String startTime;
    @NotBlank(message="걱정 종료 시간을 입력해야 합니다.")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "걱정 시간 형식은 0:00입니다")
    @Schema(description = "걱정 종료 시간", example = "12:00")
    String endTime;
}
