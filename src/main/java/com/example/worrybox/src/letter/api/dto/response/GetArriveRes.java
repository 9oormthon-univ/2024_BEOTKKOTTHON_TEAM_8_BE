package com.example.worrybox.src.letter.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetArriveRes {
    @Schema(description = "편지 도착 여부", example = "true")
    Boolean isArrived;

    public GetArriveRes(Boolean isArrived) {
        this.isArrived = isArrived;
    }
}
