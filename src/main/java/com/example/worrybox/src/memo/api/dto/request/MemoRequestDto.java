package com.example.worrybox.src.memo.api.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemoRequestDto {

    @NotBlank(message="메모 내용을 입력해야 합니다.")
    @Size(min = 1, max = 60, message = "1 글자 이상, 60 글자 이하가 아닙니다.")
    @Schema(description = "걱정 메모 내용", example = "너무 힘들어요.")
    @NotNull
    private String worryText;

    @NotBlank(message="메모 내용을 입력해야 합니다.")
    @Size(min = 1, max = 30, message = "1 글자 이상, 30 글자 이하가 아닙니다.")
    @Schema(description = "걱정 메모 해결책", example = "뭐뭐 때문인 거 같다. 이렇게 해야겠다!")
    @NotNull
    private String solution;
}
