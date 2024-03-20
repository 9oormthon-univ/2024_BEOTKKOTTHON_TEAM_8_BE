package com.example.worrybox.src.cloud.api;

import com.example.worrybox.src.cloud.application.CloudWordService;
import com.example.worrybox.src.memo.api.dto.response.CountResponseDto;
import com.example.worrybox.utils.config.BaseResponse;
import com.example.worrybox.utils.config.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CloudController {
    private final CloudWordService cloudWordService;

    @Operation(summary = "워드 클라우드 레포트", description = "걱정 메모 명사들을 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개수 반환에 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치"),
            @ApiResponse(responseCode = "8001", description = "사용자 없음 or 사용자가 쓴 메모 없음",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/cloud/{userId}/version1")
    public BaseResponse<List<String>> getCloud_1(@PathVariable Long userId) {
        try {
            return new BaseResponse<>(cloudWordService.getWordCloud_1(userId));
        } catch (Exception e) {
            BaseResponseStatus status = BaseResponseStatus.FAILED_RETURN;
            return new BaseResponse<>(status);
        }
    }

    @Operation(summary = "워드 클라우드 레포트", description = "걱정 메모 명사들의 개수 리스트를 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리스트 반환에 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/cloud/{userId}/version2")
    public BaseResponse<Map<String,Integer>> getCloud_2(@PathVariable Long userId) {
        try {
            return new BaseResponse<>(cloudWordService.getWordCloud_2(userId));
        } catch (Exception e) {
            BaseResponseStatus status = BaseResponseStatus.FAILED_RETURN;
            return new BaseResponse<>(status);
        }
    }
}
