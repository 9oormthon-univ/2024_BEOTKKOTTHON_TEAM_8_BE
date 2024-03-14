package com.example.worrybox.src.memo.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class MemoRequestDto {
    @ApiModelProperty("걱정 메모")
    private String worryText;
}
