package com.example.worrybox.src.user.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PostTimeReq {
    @ApiModelProperty("7:00")
    String startTime;
    @ApiModelProperty("21:00")
    String endTime;
}
