package com.example.worrybox.src.memo.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemoResponseDto {

    @NotNull
    @ApiModelProperty("걱정 메모")
    private String worryText;

    @NotNull
    @ApiModelProperty("걱정 해결 메모")
    private String solution;

    @NotNull
    @ApiModelProperty("생성 날짜")
    private Timestamp createdAt;
}
