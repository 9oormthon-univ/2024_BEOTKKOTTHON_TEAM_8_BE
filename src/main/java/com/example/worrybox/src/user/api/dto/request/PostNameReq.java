package com.example.worrybox.src.user.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostNameReq {
    @NotBlank(message="이름을 입력해야 합니다.")
    @Schema(description = "보관함 이름", example = "ㅇㅇ의 보관함")
    String name;
}
