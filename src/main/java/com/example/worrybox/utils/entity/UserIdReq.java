package com.example.worrybox.utils.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserIdReq {
    @ApiModelProperty(example = "7")
    private Long userId;
}
