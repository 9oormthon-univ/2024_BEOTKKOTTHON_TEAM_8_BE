package com.example.worrybox.src.user.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostNameReq {
    @NotBlank(message="이름을 입력해야 합니다.")
    @Size(min = 1, max = 5, message = "이름은 한글자 이상, 다섯글자 이하입니다.")
    @Schema(description = "보관함 이름", example = "ㅇㅇ의 보관함")
    String name;
}
