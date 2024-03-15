package com.example.worrybox.src.memo.api.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemoRequestDto {

    @NotNull
    private String worryText;

    @NotNull
    private String solution;
}
