package com.example.worrybox.src.letter.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.intellij.lang.annotations.RegExp;

@Getter
public class PostLetterReq {
    @NotBlank(message = "도착 날짜를 입력해야 합니다.")
    @Pattern(regexp = "^\\d{4}-\\d{1,2}-\\d{1,2}$", message = "도착 날짜 형식은 2023-3-16 형식입니다.")
    private String arrivalDate;
    @NotBlank(message = "편지 내용을 입력해야 합니다.")
    private String letter;
}
