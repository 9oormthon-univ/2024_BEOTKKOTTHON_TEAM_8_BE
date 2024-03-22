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
    @Schema(description = "AI 답변", example = "이렇게 이렇게 하면 될거야~")
    private String answer;
}
