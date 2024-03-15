package com.example.worrybox.src.memo.api.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemoResponseDto {

    @NotNull
    private String worryText;

    @NotNull
    private String solution;

    @NotNull
    private Timestamp createdAt;

    @NotNull
    private Long id;
}
