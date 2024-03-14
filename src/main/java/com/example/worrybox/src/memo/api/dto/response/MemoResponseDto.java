package com.example.worrybox.src.memo.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemoResponseDto {

    @ApiModelProperty("걱정 메모")
    private String worryText;

    @ApiModelProperty("생성 날짜")
    private Timestamp createdAt;
}
