package com.example.worrybox.src.user.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.aspectj.bridge.Message;

@Getter
public class PostTimeReq {
    @ApiModelProperty("7:00")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "걱정 시간 형식은 0:00입니다")
    @NotBlank(message="걱정 시작 시간을 입력해야 합니다.")
    String startTime;
    @NotBlank(message="걱정 종료 시간을 입력해야 합니다.")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "걱정 시간 형식은 0:00입니다")
    @ApiModelProperty("21:00")
    String endTime;
}
