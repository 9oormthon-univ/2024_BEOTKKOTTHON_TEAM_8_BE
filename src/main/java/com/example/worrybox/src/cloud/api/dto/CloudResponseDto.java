package com.example.worrybox.src.cloud.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudResponseDto {

    private List<Map<String, Object>> wordsList;

    @NotNull
    @Schema(description = "AI 질문(걱정 메모 내용)", example = "너무 힘들어요.")
    private String answer;

    private String maxEntry;

    public static CloudResponseDto create(List<Map<String, Object>> wordsList, String maxEntry, String answer) {
        return CloudResponseDto.builder()
                .wordsList(wordsList)
                .maxEntry(maxEntry)
                .answer(answer)
                .build();
    }
}
