package com.example.worrybox.src.cheering.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CheeringMessageRequestDto {

    @NotNull
    private String cheeringText;
}
