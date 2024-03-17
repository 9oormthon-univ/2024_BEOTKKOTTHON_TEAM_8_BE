package com.example.worrybox.src.memo.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CountResponseDto {

    @Schema(description = "3일 지난 걱정 메모 개수", example = "1")
    @NotNull
    private int count;

    public static CountResponseDto createCount(int count){
        return CountResponseDto.builder()
                .count(count)
                .build();
    }
}
