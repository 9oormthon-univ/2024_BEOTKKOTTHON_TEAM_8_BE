package com.example.worrybox.src.letter.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostLetterReq {
    @NotBlank(message = "도착 날짜를 입력해야 합니다.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "도착 날짜 형식은 2023-03-16 형식입니다.")
    @Schema(description = "편지 도착 날짜", example = "2024-03-16")
    private String arrivalDate;
    @NotBlank(message = "편지 내용을 입력해야 합니다.")
    @Schema(description = "편지 내용", example = "미래의 나 힘내라")
    @Size(max = 30, message = "편지는 최대 30글자입니다.")
    private String letter;
}
