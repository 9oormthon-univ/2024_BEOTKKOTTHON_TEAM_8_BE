package com.example.worrybox.src.user.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PostJoinReq {
    @NotBlank(message="이름을 입력해야 합니다.")
    @ApiModelProperty("OO의 보관함")
    String name;
    @ApiModelProperty("123456 (숫자 6자리)")
    int password;
    @ApiModelProperty("7:00")
    @NotBlank(message="걱정 시작 시간을 입력해야 합니다.")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "걱정 시간 형식은 0:00입니다")
    String startTime;
    @NotBlank(message="걱정 종료 시간을 입력해야 합니다.")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "걱정 시간 형식은 0:00입니다")
    @ApiModelProperty("21:00")
    String endTime;
}
