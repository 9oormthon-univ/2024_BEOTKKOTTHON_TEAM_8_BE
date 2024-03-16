package com.example.worrybox.src.cheering.api.dto.response;

import com.example.worrybox.src.cheering.domain.CheeringMessage;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheeringMessageResponseDto {

    @NotNull
    private String cheeringText;

    @NotNull
    private String name;

}
