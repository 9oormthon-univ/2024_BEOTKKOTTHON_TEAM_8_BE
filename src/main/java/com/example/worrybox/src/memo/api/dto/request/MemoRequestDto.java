package com.example.worrybox.src.memo.api.dto.request;

import com.example.worrybox.src.memo.api.dto.response.MemoResponseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoRequestDto {

    @ApiModelProperty("걱정 메모")
    private String worryText;

}
