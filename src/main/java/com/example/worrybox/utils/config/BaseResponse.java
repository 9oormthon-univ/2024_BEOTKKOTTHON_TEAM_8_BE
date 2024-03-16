package com.example.worrybox.utils.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
// BaseResponse
public class BaseResponse<T> {
    @JsonProperty("isSuccess")
    @Schema(description = "요청 처리 성공 여부", example = "true")
    private final Boolean isSuccess;
    @Schema(description = "응답 메세지", example = "요청에 성공하였습니다.")
    private final String message;
    @Schema(description = "응답 코드", example = "200")
    private final int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // Request success
    public BaseResponse(T result) {
        this.isSuccess = BaseResponseStatus.SUCCESS.isSuccess();
        this.message = BaseResponseStatus.SUCCESS.getMessage();
        this.code = BaseResponseStatus.SUCCESS.getCode();
        this.result = result;
    }

    // Request Fail
    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    public BaseResponse(BaseResponseStatus invalidParameters, String errorMessage) {
        this.isSuccess = invalidParameters.isSuccess();
        this.message = errorMessage;
        this.code = invalidParameters.getCode();
    }
}

