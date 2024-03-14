package com.example.worrybox.src.memo.api.dto.request;

import com.example.worrybox.src.memo.api.dto.response.MemoResponseDto;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemoRequestDto {

    @NotNull
    @ApiModelProperty("걱정 메모")
    private String worryText;

    @NotNull
    @ApiModelProperty("걱정 해결 메모")
    private String solution;
}
