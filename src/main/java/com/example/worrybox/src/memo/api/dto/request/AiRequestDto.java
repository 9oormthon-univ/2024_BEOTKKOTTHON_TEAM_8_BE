package com.example.worrybox.src.memo.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AiRequestDto {

    @NotNull
    @Schema(description = "AI 답변", example = "이렇게 이렇게 해보면 될거야~")
    private String question;
}
