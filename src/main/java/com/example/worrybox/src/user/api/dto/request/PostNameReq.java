package com.example.worrybox.src.user.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class PostNameReq {
    @NotBlank(message="이름을 입력해야 합니다.")
    @Pattern(regexp = "^[가-힣A-Za-z0-9]+$", message = "걱정 시간에는 한글, 영문, 숫자만 가능합니다")
    @Size(min = 1, max = 5, message = "이름은 한글자 이상, 다섯글자 이하입니다.")
    @Schema(description = "보관함 이름", example = "ㅇㅇ의 보관함")
    String name;
}
