package com.example.worrybox.src.memo.api.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemoRequestDto {

    @Schema(description = "걱정 메모 내용", example = "너무 힘들어요.")
    @NotNull
    private String worryText;

    @Schema(description = "걱정 메모 해결책", example = "뭐뭐 때문인 거 같다. 이렇게 해야겠다!")
    @NotNull
    private String solution;
}
