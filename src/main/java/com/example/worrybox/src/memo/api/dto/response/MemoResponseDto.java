package com.example.worrybox.src.memo.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemoResponseDto {
    @ApiModelProperty("걱정 메모")
    private String worryText;
}
