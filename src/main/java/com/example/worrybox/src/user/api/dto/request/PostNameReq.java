package com.example.worrybox.src.user.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostNameReq {
    @NotBlank(message="이름을 입력해야 합니다.")
    @Min(value = 1, message = "이름은 최소 한글자 이상이어야 합니다.") @Max(value = 5, message = "이름은 최대 다섯글자입니다.")
    @Schema(description = "보관함 이름", example = "ㅇㅇ의 보관함")
    String name;
}
