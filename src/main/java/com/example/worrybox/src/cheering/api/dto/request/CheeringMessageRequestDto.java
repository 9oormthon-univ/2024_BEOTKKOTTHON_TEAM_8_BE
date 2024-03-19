package com.example.worrybox.src.cheering.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CheeringMessageRequestDto {

    @NotBlank(message="응원 메세지 내용을 입력해야 합니다.")
    @Size(min = 1, max = 15, message = "1 글자 이상, 15 글자 이하가 아닙니다.")
    @NotNull
    @Schema(description = "응원 메세지", example = "얘들아 우리 모두 이겨내자.")
    private String cheeringText;
}
