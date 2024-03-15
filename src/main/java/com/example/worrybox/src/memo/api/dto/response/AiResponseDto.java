package com.example.worrybox.src.memo.api.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AiResponseDto {

    @NotNull
    private String answer;
}
