package com.example.worrybox.src.cheering.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CheeringMessageRequestDto {

    @NotNull
    @Schema(description = "응원 메세지", example = "얘들아 우리 모두 이겨내자.")
    private String cheeringText;
}
