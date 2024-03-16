package com.example.worrybox.src.memo.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AiResponseDto {

    @NotNull
    @Schema(description = "AI 질문(걱정 메모 내용)", example = "너무 힘들어요.")
    private String answer;
}
