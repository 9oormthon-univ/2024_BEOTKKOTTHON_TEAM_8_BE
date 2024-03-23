package com.example.worrybox.src.cheering.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheeringMessageResponseDto {

    @NotNull
    @Schema(description = "응원 메세지", example = "얘들아 우리 모두 이겨내자.")
    private String cheeringText;

    @NotNull
    @Schema(description = "응원 메세지 송신자", example = "인호")
    private String name;

}
